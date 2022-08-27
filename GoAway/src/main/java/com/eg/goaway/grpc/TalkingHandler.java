package com.eg.goaway.grpc;

import com.eg.gofacade.dto.GoResponse;
import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TalkingHandler implements Handler<Buffer> {
    private final String cid;
    private final StreamObserver<GoResponse> observer;
    private final GoAwayService goAwayService;

    public TalkingHandler(String cid, StreamObserver<GoResponse> observer, GoAwayService goAwayService) {
        this.cid = cid;
        this.observer = observer;
        this.goAwayService = goAwayService;
    }

    @Override
    public void handle(Buffer buffer) {
        ByteString bs = ByteString.copyFrom(buffer.getBytes());
        GoResponse rsp = GoResponse.newBuilder().setCid(cid).setStatus(200).setData(bs).build();
        try{
            observer.onNext(rsp);
        }catch (Throwable t){
            log.info("onNext:{}", t.getMessage());
        }
    }
}
