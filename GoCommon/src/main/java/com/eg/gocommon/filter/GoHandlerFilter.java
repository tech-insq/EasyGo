package com.eg.gocommon.filter;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import org.slf4j.MDC;

public class GoHandlerFilter implements Handler<Buffer> {
    private Handler<Buffer> delegate;
    private String logId;

    public GoHandlerFilter(Handler<Buffer> delegate, String logId) {
        this.delegate = delegate;
        this.logId = String.format("Go.Id.%s", logId);
    }

    @Override
    public void handle(Buffer buffer) {
        MDC.put("tid", logId);
        delegate.handle(buffer);
        MDC.remove("tid");
    }
}
