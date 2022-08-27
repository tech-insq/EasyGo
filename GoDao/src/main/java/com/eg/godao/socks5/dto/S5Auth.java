package com.eg.godao.socks5.dto;

public enum S5Auth {
    s5_auth_allow(0),
    s5_auth_deny(1);
    int v;

    S5Auth(int v) {
        this.v = v;
    }

    public int getV() {
        return v;
    }
}
