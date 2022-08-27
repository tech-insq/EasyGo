package com.eg.godao.socks5;

import com.eg.gocommon.filter.GoFactory;
import com.eg.gocommon.utils.IdUtil;
import com.eg.godao.socks5.localhandler.GoHandler;
import com.eg.godao.socks5.remotehandler.RtGoHandler;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetSocket;
import io.vertx.core.net.SocketAddress;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class Sock5Router implements Handler<NetSocket> {
    private final boolean local;
    private final String sysIdPrefix;
    private final AtomicLong socketId;
    private final Vertx vertx;
    private final ConcurrentHashMap<String, NetSocket> socketMap;
    public Sock5Router(boolean local, Vertx vertx){
        this.local = local;
        this.sysIdPrefix = IdUtil.getSysClientId();
        socketId = new AtomicLong(0);
        this.vertx = vertx;
        socketMap = new ConcurrentHashMap<>();
    }
    @Override
    public void handle(NetSocket netSocket) {
        String sysId = netSocket.writeHandlerID();
        if(!socketMap.containsKey(sysId)){
            String netSockId = String.format("%s.%d", sysIdPrefix, socketId.incrementAndGet());
            MDC.put("tid", String.format("Go.Id.%s", netSockId));
            if(local){
                localHandle(sysId, netSockId, netSocket);
            }else{
                remoteHandle(sysId, netSockId, netSocket);
            }
            MDC.clear();
        }else{
            log.warn("Go.Id={} exist", netSocket.writeHandlerID());
        }
    }

    private void remoteHandle(String sysId, String netSockId, NetSocket netSocket){
        RtGoHandler goHandler = new RtGoHandler(sysId, netSockId, netSocket, this, vertx);
        GoColseHandler closeHandler = new GoColseHandler(netSockId, goHandler);
        GoEHandler exceptionHandler = new GoEHandler(netSockId, goHandler);
        netSocket.closeHandler(GoFactory.ofClose(netSockId, closeHandler));
        netSocket.exceptionHandler(GoFactory.ofExe(netSockId, exceptionHandler));
        netSocket.handler(GoFactory.ofGo(netSockId, goHandler));
        socketMap.putIfAbsent(sysId, netSocket);
        SocketAddress address = netSocket.remoteAddress();
        log.debug("NetSocket id={}, from:{}.{}", netSockId, address.host(), address.port());
    }

    private void localHandle(String sysId, String netSockId, NetSocket netSocket){
        GoHandler goHandler = new GoHandler(sysId, netSockId, netSocket, this, vertx);
        GoColseHandler closeHandler = new GoColseHandler(netSockId, goHandler);
        GoEHandler exceptionHandler = new GoEHandler(netSockId, goHandler);

        netSocket.closeHandler(GoFactory.ofClose(netSockId, closeHandler));
        netSocket.exceptionHandler(GoFactory.ofExe(netSockId, exceptionHandler));
        netSocket.handler(GoFactory.ofGo(netSockId, goHandler));

        socketMap.putIfAbsent(sysId, netSocket);
        SocketAddress address = netSocket.remoteAddress();
        log.debug("NetSocket id={}, from:{}.{}", netSockId, address.host(), address.port());
    }

    public void remove(String id){
        socketMap.remove(id);
    }
}
