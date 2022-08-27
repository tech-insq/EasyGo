package com.eg.godns.dns;

import io.netty.handler.codec.dns.DnsQuery;
import io.netty.handler.codec.dns.DnsQuestion;
import io.netty.handler.codec.dns.DnsRecordType;
import io.netty.handler.codec.dns.DnsSection;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.dns.DnsClient;
import io.vertx.core.dns.DnsClientOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

@Slf4j
public class DnsCache {
    public final static int SINGLE_STRING = 0;
    public final static int BATCH_STRING_LIST = 1;
    private DnsClient dnsClient;
    private Map<DnsRecordType, BiConsumer<String, Handler<AsyncResult<List<String>>>>> funMap;
    private Map<DnsRecordType, BiConsumer<String, Handler<AsyncResult<String>>>> singFunMap;
    private Map<String,String> domainCache;
    public DnsCache(Vertx vertx){
        String dsnHost = "101.6.6.6";
        DnsClientOptions options = new DnsClientOptions();
        options.setHost(dsnHost);
        options.setPort(53);
        options.setQueryTimeout(1000);
        options.setLogActivity(true);
        dnsClient = vertx.createDnsClient(options);
        funMap = new HashMap<>();
        funMap.put(DnsRecordType.A, this::decoderResolveA);
        funMap.put(DnsRecordType.AAAA, dnsClient::resolveAAAA);
        funMap.put(DnsRecordType.CNAME, dnsClient::resolveCNAME);
        funMap.put(DnsRecordType.NS, dnsClient::resolveNS);
        funMap.put(DnsRecordType.TXT, dnsClient::resolveTXT);
        //funMap.put(DnsRecordType.MX, dnsClient::resolveMX);
        singFunMap = new HashMap<>();
        singFunMap.put(DnsRecordType.PTR, dnsClient::resolvePTR);

        domainCache = new HashMap<>();
        domainCache.put("easy-go.com.", "45.207.45.91");
        domainCache.put("wego.com.", "45.207.45.91");
        domainCache.put("wogo.com.", "45.207.45.91");
        domainCache.put("wigo.com.", "45.207.45.91");
    }

    public int getLookupOpt(DnsQuestion question){
        if(singFunMap.containsKey(question.type())){
            return SINGLE_STRING;
        }else{
            return BATCH_STRING_LIST;
        }
    }

    private DnsClient decoderResolveA(String var1, Handler<AsyncResult<List<String>>> var2){
        String value = domainCache.get(var1);
        if(StringUtils.hasLength(value)){
            log.debug("name=[{}] search cache", var1);
            List<String> list = new ArrayList<>(1);
            list.add(value);
            AsyncResult<List<String>> ar = new AsyncResult<>() {
                @Override
                public List<String> result() {
                    return list;
                }
                @Override
                public Throwable cause() {
                    return null;
                }
                @Override
                public boolean succeeded() {
                    return true;
                }
                @Override
                public boolean failed() {
                    return false;
                }
            };
            var2.handle(ar);
            log.info("Return cache:{}", var1);
            return dnsClient;
        }else{
            log.debug("name={} search real", var1);
            return dnsClient.resolveA(var1, var2);
        }
    }

    public void lookup(DnsQuestion question, Handler<AsyncResult<String>> handler){
        String name = question.name();
        DnsRecordType dnsType = question.type();
        BiConsumer<String, Handler<AsyncResult<String>>> fun = singFunMap.get(dnsType);
        if(fun != null){
            fun.accept(name, handler);
        }else{
            log.info("name ={} find none such type={}", name, dnsType);
        }
    }

    public void bachLookup(DnsQuestion question, Handler<AsyncResult<List<String>>> handler){
        String name = question.name();
        DnsRecordType dnsType = question.type();
        BiConsumer<String, Handler<AsyncResult<List<String>>>> fun = funMap.get(dnsType);
        if(fun != null){
            fun.accept(name, handler);
        }else{
            log.info("name ={} find none such type={}", name, dnsType);
        }
    }
}
