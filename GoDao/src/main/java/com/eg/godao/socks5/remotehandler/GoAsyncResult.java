package com.eg.godao.socks5.remotehandler;

import com.eg.gofacade.dto.GoResponse;

public class GoAsyncResult {
    private volatile boolean isSuccess;
    private volatile GoResponse response;
    private volatile Throwable thr;

    public GoAsyncResult(){
    }

    public GoResponse result(){
        return response;
    }

    public Throwable cause(){
        return thr;
    }

    public boolean failed(){
        return (!isSuccess);
    }

    public void complete(GoResponse goResponse){
        this.response = goResponse;
        this.isSuccess = true;
    }

    public void onError(Throwable thr){
        this.thr = thr;
        this.isSuccess = false;
    }
}
