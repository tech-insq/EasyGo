package com.eg.goaway.grpc;

import io.vertx.core.Handler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GoCloseHandler<T> implements Handler<T> {
    private final String cid;
    private final GoAwayService goAwayService;

    public GoCloseHandler(String cid,GoAwayService goAwayService) {
        this.cid = cid;
        this.goAwayService = goAwayService;
    }

    @Override
    public void handle(T unused) {
        try {
            if(unused instanceof Throwable){
                log.info("Exceptions:{}", ((Throwable) unused).getMessage());
            }
            goAwayService.kickOut("@GoCloseHandler", cid);
        }catch (Throwable t){
        }
    }
}
