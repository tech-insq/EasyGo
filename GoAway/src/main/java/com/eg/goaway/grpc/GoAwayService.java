package com.eg.goaway.grpc;

import com.eg.goaway.vo.GoStat;
import com.eg.gocommon.filter.GoFactory;
import com.eg.gocommon.grpc.GoContext;
import com.eg.gofacade.dto.*;
import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;
import io.vertx.core.net.SocketAddress;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class GoAwayService extends GoServiceGrpc.GoServiceImplBase {
    private final Vertx vertx;
    private final ConcurrentHashMap<String, GoChannel> goMap;
    public GoAwayService(Vertx vertx){
        this.vertx = vertx;
        this.goMap = new ConcurrentHashMap<>();
    }

    @Override
    public void hello(HelloRequest request, StreamObserver<GoResponse> observer) {
        String word = request.getWord();
        log.info("Hello:{}", request.getCid(), word);
        successRsp(request.getCid(), ByteString.copyFrom(word, StandardCharsets.UTF_8), observer);
        observer.onCompleted();
    }

    @Override
    public void ask(AskRequest ask, StreamObserver<GoResponse> observer) {
        NetClient netClient = vertx.createNetClient();
        if((ask.getPort() < 0) || (ask.getPort() > 65535)) {
            log.info("Connect to:{}.{}", ask.getHost(), ask.getPort());
        }
        SocketAddress inetSocketAddress = SocketAddress.inetSocketAddress(ask.getPort(), ask.getHost());
        netClient.connect(inetSocketAddress, GoFactory.ofConnect(ask.getCid(), ar -> {
            try {
                if (ar.failed()) {
                    log.debug("Connect to={}:{} failed, exceptions:{}", ask.getHost(), ask.getPort(), ar.cause().getMessage());
                    failRsp(ask.getCid(), GoStatus.ST_SRV_UA_VALUE, observer);
                    netClient.close();
                } else {
                    log.debug("Connect to={}:{} success.", ask.getHost(), ask.getPort());
                    NetSocket netSocket = ar.result();
                    GoChannel goChannel = new GoChannel(ask.getCid(), GoStat.TRANSFERRING.getSt(), netSocket);
                    goMap.put(ask.getCid(), goChannel);
                    /**set close handler**/
                    netSocket.closeHandler(GoFactory.ofGrpc(ask.getCid(), new GoCloseHandler<>(ask.getCid(), this)));
                    netSocket.exceptionHandler(GoFactory.ofGrpc(ask.getCid(), new GoCloseHandler<>(ask.getCid(), this)));
                    /**response**/
                    successRsp(ask.getCid(), ByteString.EMPTY, observer);
                }
                observer.onCompleted();
            }catch (Throwable t){
                log.debug(">>>>>>>");
            }
        }));
    }

    @Override
    public StreamObserver<TalkRequest> talk(StreamObserver<GoResponse> observer) {
        String invalidCid = "NA";
        String cid = GoContext.getCid();
        if(!StringUtils.hasLength(cid)){
            log.warn("Cid is null");
            cid = invalidCid;
        }
        TalkRequestStream talkRequestStream = new TalkRequestStream(cid,this, observer);
        if(!cid.equals(invalidCid)){
            talkRequestStream.bindChannel();
        }
        return talkRequestStream;
    }

   @Override
    public void bye(ByeRequest byeRequest, StreamObserver<GoResponse> observer) {
        MDC.put("tid", byeRequest.getCid());
        try {
            successRsp(byeRequest.getCid(), ByteString.EMPTY, observer);
            kickOut("bye", byeRequest.getCid());
            observer.onCompleted();
        }catch (Throwable t){
            log.debug(">>>>>>>");
        }
        MDC.clear();
    }

    public GoChannel getGoChannel(String cid){
        return goMap.get(cid);
    }

    public void kickOut(String from,String cid){
        log.debug("kickOut: cid={} from={}", cid, from);
        closeCid(cid);
    }

    private void closeCid(String cid){
        GoChannel goChannel = goMap.remove(cid);
        if(goChannel != null){
            goChannel.close();
        }
    }

    private void failRsp(String cid, int code, StreamObserver<GoResponse> observer){
        GoResponse rsp = GoResponse.newBuilder().setCid(cid).setStatus(code).setData(ByteString.EMPTY).build();
        observer.onNext(rsp);
    }

    private void successRsp(String cid, ByteString bs, StreamObserver<GoResponse> observer){
        GoResponse rsp = GoResponse.newBuilder().setCid(cid).setStatus(200).setData(bs).build();
        observer.onNext(rsp);
    }
}
