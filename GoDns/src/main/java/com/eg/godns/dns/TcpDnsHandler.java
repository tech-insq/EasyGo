package com.eg.godns.dns;

import com.eg.gocommon.filter.GoFactory;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetSocket;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class TcpDnsHandler implements Handler<NetSocket> {
    private final AtomicLong tcpSeq;
    private final DnsCache dnsCache;
    private final Vertx vertx;

    public TcpDnsHandler(DnsCache dnsCache, Vertx vertx) {
        this.tcpSeq = new AtomicLong(0);
        this.dnsCache = dnsCache;
        this.vertx = vertx;
    }

    @Override
    public void handle(NetSocket socket) {
        String logId = String.format("TCP.%d", tcpSeq.incrementAndGet());
        log.info("Sock={} connect", logId);
        socket.handler(GoFactory.ofGo(logId, new TcpDnsSockHandler(socket)));
    }
}
