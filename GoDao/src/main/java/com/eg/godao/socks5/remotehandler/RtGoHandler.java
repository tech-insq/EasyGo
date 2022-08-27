package com.eg.godao.socks5.remotehandler;

import com.eg.gocommon.filter.GoFactory;
import com.eg.gocommon.grpc.GoContext;
import com.eg.godao.socks5.Sock5Handler;
import com.eg.godao.socks5.Sock5Router;
import com.eg.godao.socks5.dto.S5Constant;
import com.eg.godao.socks5.dto.S5Stat;
import com.eg.gofacade.dto.*;
import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class RtGoHandler extends Sock5Handler {
    private final AtomicBoolean first;
    private volatile int seq;
    private volatile StreamObserver<TalkRequest> talkOutStream;
    public RtGoHandler(String sysId, String logId, NetSocket socket, Sock5Router router,Vertx vertx){
        super(sysId, logId, socket, router, vertx);
        first = new AtomicBoolean(true);
        seq = 0;
    }

    @Override
    protected void transferData(Buffer buffer) {
        ByteString data = ByteString.copyFrom(buffer.getBytes());
        seq ++;
        TalkRequest req = TalkRequest.newBuilder().setCid(logId).setData(data).setSeq(seq).build();
        talkOutStream.onNext(req);
    }

    @Override
    protected void connect() {
        AskRequest ask = AskRequest.newBuilder().setCid(logId).setHost(connectAdr.getHost())
                .setPort(connectAdr.getPort()).build();
        if((connectAdr.getPort() < 0) || (connectAdr.getPort() > 65535)) {
            log.info("Connect to:{}.{}", connectAdr.getHost(), connectAdr.getPort());
        }
        RtCall.ask(ask, GoFactory.ofGrpc(logId, ar->{
            if(ar.failed()) {
                log.debug("connect to={}:{} failed:{}", connectAdr.getHost(), connectAdr.getPort(),
                        ar.cause().getMessage());
                failResp(S5Constant.CONNECT_L_ERR);
            }else{
                GoResponse response = ar.result();
                if(response.getStatus() == GoStatus.ST_OK_VALUE) {
                    log.debug("connect to={}:{} success", connectAdr.getHost(), connectAdr.getPort());
                    GoPipToLocal goPipToLocal = new GoPipToLocal(logId, this);
                    GoContext.setCid(logId);
                    talkOutStream = RtCall.talk(goPipToLocal);
                    successRsp();
                    stat = S5Stat.TRANSFERRING.getSt();
                }else{
                    log.debug("connect to={}:{} failed", connectAdr.getHost(), connectAdr.getPort());
                    failResp(S5Constant.CONNECT_L_ERR);
                }
            }
        }));
    }
    @Override
    protected void close(String from) {
        if(first.getAndSet(false)) {
            log.debug("From:{} from close");
            try {
                if(talkOutStream != null) {
                    ByeRequest request = ByeRequest.newBuilder().setCid(logId).build();
                    RtCall.bye(request, r->{
                        quiteComplete();
                    });
                }
            }catch (Throwable t){
                log.info("close: exceptions:{}", t.getMessage());
            }
        }
    }

    private void quiteComplete(){
        try{
            talkOutStream.onCompleted();
        }catch (Throwable t){
        }
    }
}
