package com.eg.godao.socks5.dto;

public class S5Constant {
    public static final byte S5VER = 0x05;
    public static final byte S5_NO_AUTH = 0x00;
    public static final byte S5_CMD = 0x01;
    public static final byte ADR_IPV4 = 0x01;
    public static final byte ADR_DOMAIN = 0x03;
    public static final byte ADR_IPV6 = 0x04;

    public static final byte CONNECT_OK = 0x00;
    public static final byte CONNECT_L_ERR = 0x01;
    public static final byte CONNECT_REJ = 0x04;
    public static final byte S5_RSV = 0x00;
}
