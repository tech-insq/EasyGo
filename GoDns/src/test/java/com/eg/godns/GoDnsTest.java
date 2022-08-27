package com.eg.godns;

import io.vertx.core.Vertx;
import io.vertx.core.dns.DnsClient;
import io.vertx.core.dns.DnsClientOptions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class GoDnsTest {
    private Vertx vertx;
    private DnsClient client;

    public GoDnsTest(){
        String host = "8.8.8.8";
        vertx = Vertx.vertx();
        client = vertx.createDnsClient(new DnsClientOptions().setPort(53).setHost("127.0.0.1").setQueryTimeout(10000));
    }

    @Test
    public void testGetDNS() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        client.lookup4("vertx.io", ar->{
            if(ar.succeeded()){
                System.out.println(">>>>> Success:" + ar.result());
            }else{
                System.out.println(">>>> failed:" + ar.cause());
            }
            latch.countDown();
        });
        latch.await();
    }

    @Test
    public void reverseLookup() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        client.resolvePTR("110.242.68.66", ar->{
            if(ar.succeeded()){
                System.out.println(">>>>> Success:" + ar.result());
            }else{
                System.out.println(">>>> failed:" + ar.cause());
            }
            latch.countDown();
        });
        latch.await();
    }

    @Test
    public void resolve() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        client.resolveA("askdao.top", ar -> {
            if (ar.succeeded()) {
                List<String> records = ar.result();
                for (String record : records) {
                    System.out.println("resolve:" + record);
                }
            } else {
                System.out.println("Failed to resolve entry" + ar.cause());
            }
            latch.countDown();
        });
        latch.await();
    }

    @Test
    public void testCNName() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        client.resolveCNAME("askdao.top", ar -> {
            if (ar.succeeded()) {
                List<String> records = ar.result();
                System.out.println("CNName size:" + records.size());
                for (String record : records) {
                    System.out.println("CNName" + record);
                }
            } else {
                System.out.println("Failed to resolve entry" + ar.cause());
            }
            latch.countDown();
        });
        latch.await();
    }

    @Test
    public void resolveNS() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        client.resolveNS("baidu.com", ar -> {
            if (ar.succeeded()) {
                List<String> records = ar.result();
                System.out.println("resolveNS size:" + records.size());
                for (String record : records) {
                    System.out.println("resolveNS:" + record);
                }
            } else {
                System.out.println("Failed to resolve entry" + ar.cause());
            }
            latch.countDown();
        });
        latch.await();
    }

    @Test
    public void resolveTXT() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        client.resolveTXT("baidu.com", ar -> {
            if (ar.succeeded()) {
                List<String> records = ar.result();
                System.out.println("resolveTXT size:" + records.size());
                for (String record : records) {
                    System.out.println("resolveTXT:" + record);
                }
            } else {
                System.out.println("Failed to resolve entry" + ar.cause());
            }
            latch.countDown();
        });
        latch.await();
    }
}
