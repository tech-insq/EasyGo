package com.eg.goaway.vo;

public enum GoStat {
    IDLE(0),
    AUTH(1),
    CONNECTING(2),
    TRANSFERRING(3),
    CLOSE(4);
    GoStat(int st) {
        this.st = st;
    }

    public int getSt() {
        return st;
    }

    private int st;
}
