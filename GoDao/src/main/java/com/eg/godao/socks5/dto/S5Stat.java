package com.eg.godao.socks5.dto;

public enum S5Stat {
    IDLE(0),
    AUTH(1),
    CONNECTING(2),
    TRANSFERRING(3),
    CLOSE(4);
    S5Stat(int st) {
        this.st = st;
    }

    public int getSt() {
        return st;
    }

    private int st;
}
