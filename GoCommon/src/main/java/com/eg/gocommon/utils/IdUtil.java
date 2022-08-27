package com.eg.gocommon.utils;

import java.util.concurrent.ThreadLocalRandom;

public class IdUtil {
    private static volatile String sysClientId = null;
    public static String getSysClientId(){
        if(sysClientId == null){
            synchronized (IdUtil.class){
                if(sysClientId == null){
                    sysClientId = getId();
                }
            }
        }
        return sysClientId;
    }
    private static String getId(){
        ThreadLocalRandom random = ThreadLocalRandom.current();
        long value = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        sb.append(to62(value)).append(".").append(to62(random.nextLong(100000)));
        return sb.toString();
    }

    private static String to62(long value){
        StringBuilder sb = new StringBuilder();
        while((value > 0)){
            int m = (int)(value % 62);
            value = value/62;
            if(m < 10){
                sb.append(m);
            }else if(m < 36){
                sb.append((char)('a' + (m - 10)));
            }else{
                sb.append((char) ('A' + (m - 36)));
            }
        }
        return sb.toString();
    }
}
