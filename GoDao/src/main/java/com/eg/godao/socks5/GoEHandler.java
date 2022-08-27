package com.eg.godao.socks5;

import io.vertx.core.Handler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GoEHandler implements Handler<Throwable> {
    private final String logId;
    private final Sock5Handler goHandler;

    public GoEHandler(String logId, Sock5Handler goHandler) {
        this.logId = logId;
        this.goHandler = goHandler;
    }

    @Override
    public void handle(Throwable throwable) {
        log.warn("Go.Id={}, Exceptions:{}", logId, throwable.getMessage());
        goHandler.closeAll("GoEHandler");
    }
}
