package com.eg.godao.socks5.localhandler;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GoLocalHandler implements Handler<Buffer> {
    private NetSocket local;

    public GoLocalHandler(NetSocket local) {
        this.local = local;
    }

    @Override
    public void handle(Buffer buffer) {
        local.write(buffer);
    }
}
