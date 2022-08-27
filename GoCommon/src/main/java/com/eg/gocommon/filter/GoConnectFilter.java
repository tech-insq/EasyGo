package com.eg.gocommon.filter;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.net.NetSocket;
import org.slf4j.MDC;

public class GoConnectFilter implements Handler<AsyncResult<NetSocket>> {
    private String logId;
    private Handler<AsyncResult<NetSocket>> delegate;

    public GoConnectFilter(String logId, Handler<AsyncResult<NetSocket>> delegate) {
        this.logId = logId;
        this.delegate = delegate;
    }

    @Override
    public void handle(AsyncResult<NetSocket> rc) {
        MDC.put("tid", logId);
        delegate.handle(rc);
        MDC.remove("tid");
    }
}
