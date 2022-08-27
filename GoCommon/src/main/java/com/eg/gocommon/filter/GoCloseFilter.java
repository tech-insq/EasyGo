package com.eg.gocommon.filter;

import io.vertx.core.Handler;
import org.slf4j.MDC;

public class GoCloseFilter implements Handler<Void> {
    private Handler<Void> delegate;
    private String logId;

    public GoCloseFilter(Handler<Void> delegate, String logId) {
        this.delegate = delegate;
        this.logId = logId;
    }

    @Override
    public void handle(Void unused) {
        MDC.put("tid", logId);
        delegate.handle(unused);
        MDC.remove("tid");
    }
}
