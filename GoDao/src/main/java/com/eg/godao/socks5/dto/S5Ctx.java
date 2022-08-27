package com.eg.godao.socks5.dto;

import lombok.Data;

@Data
public class S5Ctx {
    byte state;
    byte methods;
    byte cmd;
    byte atyp;
    byte userlen;
    byte passlen;
    short dport;
    byte[] username;
    byte[] password;
    byte[] daddr;
    public S5Ctx(){
        username = new byte[257];
        password = new byte[257];
        daddr    = new byte[257];
    }
}
