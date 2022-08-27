package com.eg.gocommon.filter;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;
import org.slf4j.MDC;

public class GoFactory {
    public static Handler<Void> ofClose(String logId, Handler<Void> handler){
        return new GoCloseFilter(handler, logId);
    }

    public static Handler<Throwable> ofExe(String logId, Handler<Throwable> handler){
        return new GoExceptionFilter(logId, handler);
    }

    public static Handler<Buffer> ofGo(String logId, Handler<Buffer> handler){
        return new GoHandlerFilter(handler, logId);
    }

    public static Handler<AsyncResult<NetSocket>> ofConnect(String logId, Handler<AsyncResult<NetSocket>> handler){
        return new GoConnectFilter(logId, handler);
    }

    public static <T> Handler<AsyncResult<T>> ofEcho(String logId, Handler<AsyncResult<T>> handler){
        return rc -> {
            MDC.put("tid", logId);
            handler.handle(rc);
            MDC.remove("tid");
        };
    }

    public static <T> Handler<T> ofGrpc(String logId, Handler<T> handler){
        return rc -> {
            MDC.put("tid", logId);
            handler.handle(rc);
            MDC.remove("tid");
        };
    }
}
