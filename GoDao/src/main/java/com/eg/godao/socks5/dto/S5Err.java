package com.eg.godao.socks5.dto;

public enum S5Err {
    BAD_VERSION(-1, "Bad protocol version."),
    BAD_CMD(-2, "Bad protocol command."),
    BAD_ATYP(-3, "Bad address type."),
    OK(0, "No error."),
    AUTH_SELECT(1, "Select authentication method."),
    AUTH_VERIFY(2, "Verify authentication."),
    EXEC_CMD(3, "Execute command.");
    int c;
    String msg;
    S5Err(int c, String msg) {
        this.c = c;
        this.msg = msg;
    }
}
