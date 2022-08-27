package com.eg.godns;

import com.eg.godns.dns.DnsCache;
import com.eg.godns.dns.TcpDnsHandler;
import com.eg.godns.dns.UdpDnsHandler;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.datagram.DatagramSocket;
import io.vertx.core.datagram.DatagramSocketOptions;
import io.vertx.core.net.NetServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PreDestroy;
import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {

    private CountDownLatch latch;
    public static void main(String args[]){
        System.setProperty("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.SLF4JLogDelegateFactory");
        SpringApplication.run(Application.class, args);
    }

    @PreDestroy
    public void destroy(){
        if(latch != null){
            latch.countDown();
        }
    }

    @Override
    public void run(String... args) {
        latch = new CountDownLatch(1);
        VertxOptions vertxOptions = new VertxOptions();
        vertxOptions.setEventLoopPoolSize(8);
        vertxOptions.setWorkerPoolSize(40);
        Vertx vertx = Vertx.vertx(vertxOptions);
        DnsCache dnsCache = new DnsCache(vertx);
        NetServer tcp = vertx.createNetServer();
        tcp.connectHandler(new TcpDnsHandler(dnsCache, vertx)).exceptionHandler(r-> log.warn("Get an exceptions:{}", r.getMessage()));
        DatagramSocket udp = vertx.createDatagramSocket(new DatagramSocketOptions());
        udp.handler(new UdpDnsHandler(dnsCache, udp));
        try{
            log.info("GoDns listen on 53");
            tcp.listen(53).onSuccess(r-> log.info("dns listen on tcp port 53"));
            udp.listen(53, "0.0.0.0").onSuccess(r-> log.info("dns listen on udp port 53"));
            log.info("GoDns await");
            latch.await();
        }catch (Throwable t){
            log.info("GoDns exceptions:{}", t.getMessage());
        }
        tcp.close();
        log.info("GoDns quit");
    }
}
