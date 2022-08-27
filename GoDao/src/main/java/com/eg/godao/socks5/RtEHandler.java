package com.eg.godao.socks5;

import io.vertx.core.Handler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RtEHandler implements Handler<Throwable> {
    private final String logId;
    private final Sock5Handler goHandler;

    public RtEHandler(String logId, Sock5Handler goHandler) {
        this.logId = logId;
        this.goHandler = goHandler;
    }

    @Override
    public void handle(Throwable throwable) {
        log.info("Go.Id={}, get exceptions:{}", logId, throwable.getMessage());
        goHandler.closeAll("RtEHandler");
    }
}
