package com.eg.gocommon.filter;

import io.vertx.core.Handler;
import org.slf4j.MDC;

public class GoExceptionFilter implements Handler<Throwable> {
    private String logId;
    private Handler<Throwable> delegate;

    public GoExceptionFilter(String logId, Handler<Throwable> delegate) {
        this.logId = logId;
        this.delegate = delegate;
    }

    @Override
    public void handle(Throwable throwable) {
        MDC.put("tid", logId);
        delegate.handle(throwable);
        MDC.remove("tid");
    }
}
