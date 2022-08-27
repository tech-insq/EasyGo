package com.eg.godns.dns;

import com.eg.godns.dns.util.DnsMessageUtil;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.dns.*;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TcpDnsSockHandler implements Handler<Buffer>{
    private final static int FIX_F_LEN = 2;
    private final DnsRecordDecoder decoder;
    private final NetSocket socket;
    private short len;
    public TcpDnsSockHandler(NetSocket socket){
        decoder = DnsRecordDecoder.DEFAULT;
        this.socket = socket;
        len = -1;
    }

    @Override
    public void handle(Buffer buffer) {
        log.info("In bufflen={}", buffer.length());
        int skip = 0;
        if(len == -1){
            if(buffer.length() < 2){
                log.info("Len not eq 2");
                socket.pause();
                return;
            }
            len = buffer.getShort(0);
        }else{
            skip = 2;
        }

        log.info("Buflen={}, dns len={}, skip={}", buffer.length(), len, skip);
        if((buffer.length() + skip) < len){
            if(skip == 0){
                len = -1;
            }
            socket.pause();
            return;
        }

        int offset = (FIX_F_LEN - skip);
        Buffer outBuf = buffer.slice(offset, offset + len);
        ByteBuf byteBuf = outBuf.getByteBuf();
        try {
            DnsQuery dnsQuery = DnsMessageUtil.decodeDnsQuery(this.decoder, byteBuf, (id, dnsOpCode) -> new DefaultDnsQuery(id, dnsOpCode));
            DnsQuestion question = dnsQuery.recordAt(DnsSection.QUESTION);
            log.info("count={}, question:{}, opCode={}", dnsQuery.count(), question.toString(),
                    dnsQuery.opCode().toString());
        } catch (Exception e) {
            log.info("decodeDnsQuery:{}", e.getMessage());
        }
    }
}
