package com.eg.godao.socks5;

import io.vertx.core.Handler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RtCloseHandler implements Handler<Void> {
    private final String logId;
    private final Sock5Handler goHandler;

    public RtCloseHandler(String logId, Sock5Handler goHandler) {
        this.logId = logId;
        this.goHandler = goHandler;
    }

    @Override
    public void handle(Void unused) {
        log.debug("Go.Id={} remote close", logId);
        goHandler.closeAll("RtCloseHandler");
    }
}
