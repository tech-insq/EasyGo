package com.eg.godao.socks5.dto;

import lombok.Data;

@Data
public class ConnectAdr {
    private byte atyp;
    private short port;
    private String host;
    private byte [] taddr;
}
