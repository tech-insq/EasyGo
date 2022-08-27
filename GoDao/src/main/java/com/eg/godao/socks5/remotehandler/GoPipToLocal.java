package com.eg.godao.socks5.remotehandler;

import com.eg.godao.socks5.Sock5Handler;
import com.eg.gofacade.dto.GoResponse;
import io.grpc.stub.StreamObserver;
import io.vertx.core.buffer.Buffer;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class GoPipToLocal implements StreamObserver<GoResponse> {
    private final AtomicBoolean first;
    private final AtomicBoolean isOk;
    private final String cid;
    private final Sock5Handler sock5Handler;

    public GoPipToLocal(String cid, Sock5Handler sock5Handler) {
        this.first = new AtomicBoolean(true);
        this.isOk  = new AtomicBoolean(true);
        this.cid   = cid;
        this.sock5Handler = sock5Handler;
    }

    @Override
    public void onNext(GoResponse goResponse) {
        if(isOk.get()) {
            Buffer outBuf = Buffer.buffer(goResponse.getData().toByteArray());
            sock5Handler.pipToLocal(outBuf);
        }else{
            log.info("Cid={}, pip to local stream is closed", cid);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        if(first.getAndSet(false)) {
            MDC.put("tid", cid);
            log.info("onError:", throwable);
            sock5Handler.closeAll("GoPipToLocal.onError");
            isOk.set(false);
            MDC.remove("tid");
        }
    }

    @Override
    public void onCompleted() {
        if(first.getAndSet(false)) {
            MDC.put("tid", cid);
            sock5Handler.closeAll("GoPipToLocal.onCompleted");
            isOk.set(false);
            MDC.remove("tid");
        }
    }
}
