package com.eg.godao.socks5.remotehandler;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ErrorIpCache {
    private static final Long MAX_TTL = TimeUnit.MINUTES.toMillis(5);
    private static final ConcurrentHashMap<String, Long> errorIp = new ConcurrentHashMap<>();
    public static boolean isHit(String ip, int port){
        StringBuilder sb = new StringBuilder();
        sb.append(ip).append(":").append(port);
        String host = sb.toString();
        Long value = errorIp.get(host);
        if(value == null){
            return false;
        }
        if((System.currentTimeMillis() - value) >= MAX_TTL){
            errorIp.remove(host);
            return false;
        }
        return true;
    }

    public static void put(String ip, int port){
        StringBuilder sb = new StringBuilder();
        sb.append(ip).append(":").append(port);
        String host = sb.toString();
        errorIp.putIfAbsent(host, Long.valueOf(System.currentTimeMillis()));
    }
}
