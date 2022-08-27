package com.eg.godao.socks5.remotehandler;

import com.eg.gofacade.dto.GoResponse;
import io.grpc.stub.StreamObserver;
import io.vertx.core.Handler;

public class GoSSObserver implements StreamObserver<GoResponse> {
    private GoAsyncResult result;
    private final Handler<GoAsyncResult> handler;

    public GoSSObserver(Handler<GoAsyncResult> handler) {
        result = new GoAsyncResult();
        result.onError(new Exception("Bad order response"));
        this.handler = handler;
    }

    @Override
    public void onNext(GoResponse goResponse) {
        result.complete(goResponse);
        handler.handle(result);
    }

    @Override
    public void onError(Throwable throwable) {
        result.onError(throwable);
        handler.handle(result);
    }

    @Override
    public void onCompleted() {
    }
}
