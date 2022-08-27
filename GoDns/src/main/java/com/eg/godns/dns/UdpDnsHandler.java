package com.eg.godns.dns;

import com.eg.godns.dns.util.DnsCodecUtil;
import com.eg.godns.dns.util.DnsMessageUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.dns.*;
import io.netty.util.NetUtil;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.datagram.DatagramPacket;
import io.vertx.core.datagram.DatagramSocket;
import io.vertx.core.dns.DnsException;
import io.vertx.core.net.SocketAddress;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class UdpDnsHandler implements Handler<DatagramPacket> {
    private final DnsRecordDecoder decoder;
    private final DnsRecordEncoder encoder;
    private final AtomicLong udpSeq;
    private final DnsCache dnsCache;
    private final DatagramSocket udp;
    public UdpDnsHandler(DnsCache dnsCache, DatagramSocket udp){
        this.decoder = DnsRecordDecoder.DEFAULT;
        this.encoder = DnsRecordEncoder.DEFAULT;
        this.udpSeq = new AtomicLong(0);
        this.dnsCache = dnsCache;
        this.udp = udp;
    }

    @Override
    public void handle(DatagramPacket packet) {
        try {
            DnsQuery query = DnsMessageUtil.decodeDnsQuery(decoder, packet.data().getByteBuf(),
                    (id, dnsOpCode) -> new DefaultDnsQuery(id, dnsOpCode));
            if(query.opCode().compareTo(DnsOpCode.QUERY) == 0) {
                DnsQuestion question = query.recordAt(DnsSection.QUESTION);
                int type = dnsCache.getLookupOpt(question);
                if(type == DnsCache.SINGLE_STRING){
                    dnsCache.lookup(question, ar->singleResp(packet, query, ar));
                }else if(type == DnsCache.BATCH_STRING_LIST) {
                    dnsCache.bachLookup(question, ar -> batchResp(packet, query, ar));
                }else{
                    log.info("Question type={}, error type={}", question.type(), type);
                }
            }
        } catch (Exception e) {
            log.info("Udp:{}:{}, Exceptions:{}", packet.sender().hostAddress(), packet.sender().port(), e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void singleResp(DatagramPacket packet, DnsQuery query, AsyncResult<String> ar){
        DnsQuestion question = query.recordAt(DnsSection.QUESTION);
        DefaultDnsResponse response = new DefaultDnsResponse(query.id());
        response.addRecord(DnsSection.QUESTION, question);
        if(ar.succeeded()){
            String value = ar.result();
            ByteBuf bbf = Unpooled.buffer();
            if(question.type().equals(DnsRecordType.PTR)) {
                DnsCodecUtil.encodeDomainName(value, bbf);
            }else {
                byte [] tmp = value.getBytes();
                bbf.writeByte(tmp.length).writeBytes(tmp);
            }
            DefaultDnsRawRecord ans = new DefaultDnsRawRecord(question.name(), question.type(), question.dnsClass(), 600, bbf);
            response.addRecord(DnsSection.ANSWER, ans);
        }else{
            if(ar.cause() instanceof DnsException) {
                DnsException exception = (DnsException)ar.cause();
                response.setCode(DnsResponseCode.valueOf(exception.code().code()));
            }else{
                response.setCode(DnsResponseCode.SERVFAIL);
            }
        }
        response(packet, response);
    }

    private void batchResp(DatagramPacket packet, DnsQuery query, AsyncResult<List<String>> ar){
        DnsQuestion question = query.recordAt(DnsSection.QUESTION);
        DefaultDnsResponse response = new DefaultDnsResponse(query.id());
        response.addRecord(DnsSection.QUESTION, question);
        if(ar.succeeded()){
            log.debug("name={}, type={}, result={}", question.name(), question.type(), ar.result().size());
            for(String ip: ar.result()) {
                log.debug("name={}, result={}", question.name(), ip);
                ByteBuf bbf = Unpooled.buffer();
                if(question.type().equals(DnsRecordType.A) || question.type().equals(DnsRecordType.AAAA)) {
                    byte [] adr = NetUtil.createByteArrayFromIpAddressString(ip);
                    bbf.writeBytes(adr);
                }else if(question.type().equals(DnsRecordType.NS) || (question.type().equals(DnsRecordType.CNAME))){
                    DnsCodecUtil.encodeDomainName(ip, bbf);
                }else {
                    byte [] adr = ip.getBytes();
                    bbf = Unpooled.buffer(1 + adr.length);
                    bbf.writeByte(adr.length).writeBytes(adr);
                }
                DefaultDnsRawRecord ans = new DefaultDnsRawRecord(question.name(), question.type(), question.dnsClass(), 600, bbf);
                response.addRecord(DnsSection.ANSWER, ans);
            }
        }else{
            log.info("name={}, failed,result={}", question.name(), ar.cause());
            if(ar.cause() instanceof DnsException) {
                DnsException exception = (DnsException)ar.cause();
                response.setCode(DnsResponseCode.valueOf(exception.code().code()));
            }else{
                response.setCode(DnsResponseCode.SERVFAIL);
            }
        }
        response(packet, response);
    }

    private void response(DatagramPacket packet, DefaultDnsResponse response){
        try{
            ByteBuf outBuf = Unpooled.buffer();
            DnsMessageUtil.encodeDnsResponse(encoder, response, outBuf);
            SocketAddress sender = packet.sender();
            udp.send(Buffer.buffer(outBuf), sender.port(), sender.hostAddress());
        }catch (Throwable t){
            log.warn("encodeDnsResponse exceptions:{}", t.getMessage());
        }
    }
}
