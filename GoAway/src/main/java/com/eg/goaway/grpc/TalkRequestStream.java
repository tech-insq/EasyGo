package com.eg.goaway.grpc;

import com.eg.gofacade.dto.GoResponse;
import com.eg.gofacade.dto.TalkRequest;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class TalkRequestStream implements StreamObserver<TalkRequest> {
    private volatile int talkTimes;
    private final AtomicBoolean firstTalk;
    private final AtomicBoolean firstClose;
    private final GoAwayService goAwayService;
    private final StreamObserver<GoResponse> observer;
    private final AtomicReference<GoChannel> goChannelRef;
    private volatile String cid;
    public TalkRequestStream(String cid,GoAwayService goAwayService, StreamObserver<GoResponse> observer) {
        this.firstTalk  = new AtomicBoolean(true);
        this.firstClose = new AtomicBoolean(true);
        this.goAwayService = goAwayService;
        this.observer = observer;
        this.goChannelRef = new AtomicReference<>(null);
        this.cid = cid;
    }

    public void bindChannel(){
        GoChannel goChannel = goAwayService.getGoChannel(cid);
        if(goChannel != null) {
            goChannel.bindHandler(observer, goAwayService);
            goChannelRef.getAndSet(goChannel);
        }else{
            log.info("Find none such channel");
        }
    }

    @Override
    public void onNext(TalkRequest req) {
        talkTimes++;
        MDC.put("tid", cid);
        if((req.getSeq() == 1) && (goChannelRef.get() == null)){
            if(firstTalk.getAndSet(false)) {
                cid = req.getCid();
                bindChannel();
            }
        }

        GoChannel goChannel = goChannelRef.get();
        if(goChannel != null) {
            goChannel.talkTo(req);
        }else{
            log.warn("Cid:{}, talkTimes:{}, seq={} is error", req.getCid(), talkTimes,
                    req.getSeq());
            observer.onError(new Throwable("Error msg order"));
            goAwayService.kickOut("onNext", req.getCid());
        }
        MDC.remove("tid");
    }

    @Override
    public void onError(Throwable throwable) {
        if(firstClose.getAndSet(false)) {
            MDC.put("tid", cid);
            log.warn("onError:{}", throwable.getMessage());
            log.warn("onCompleted: talkTimes={}", talkTimes);
            quiteComplete();
            goAwayService.kickOut("onError", cid);
            MDC.remove("tid");
        }
    }

    @Override
    public void onCompleted() {
        if(firstClose.getAndSet(false)) {
            MDC.put("tid", cid);
            if(goChannelRef.get() == null) {
                log.info("onCompleted: talkTimes={}", talkTimes);
            }
            quiteComplete();
            goAwayService.kickOut("onCompleted", cid);
            MDC.remove("tid");
        }
    }

    private void quiteComplete(){
        try{
            observer.onCompleted();
        }catch (Throwable t){
        }
    }
}
