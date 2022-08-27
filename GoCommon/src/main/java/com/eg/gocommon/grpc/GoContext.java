package com.eg.gocommon.grpc;

public class GoContext {
    private final static ThreadLocal<String> cidLocal = new ThreadLocal<>();
    public static String getCid(){
        return cidLocal.get();
    }

    public static void setCid(String cid){
        cidLocal.set(cid);
    }
}
