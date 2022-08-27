package com.eg.godao;

import com.eg.godao.socks5.Sock5Router;
import com.eg.godao.socks5.remotehandler.RtCall;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.net.NetServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PreDestroy;
import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {

    @Value("${easy.go.local:true}")
    private boolean local;
    @Value("${easy.go.remote.host:wogo.com}")
    private String host;
    @Value("${easy.go.remote.port:8443}")
    private int port;
    @Value("${easy.go.remote.certPath:./tls}")
    private String certPath;
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
        log.info("host:{}, port:{}, certPath:{}, local:{}", host, port, certPath, local);
        latch = new CountDownLatch(1);
        RtCall rtCall = new RtCall(host, port, certPath);
        rtCall.start();

        VertxOptions options = new VertxOptions();
        options.setEventLoopPoolSize(8);
        Vertx vertx = Vertx.vertx(options);

        NetServer server = vertx.createNetServer();
        server.connectHandler(new Sock5Router(local, vertx)).exceptionHandler(r-> log.warn("Get an exceptions:{}", r.getMessage()));
        log.info("GoDao listen on 1080");
        server.listen(1080);
        try{
            log.info("GoDao await");
            latch.await();
        }catch (Throwable t){
            log.info("GoDao exceptions:{}", t.getMessage());
        }
        server.close();
        rtCall.close();
        log.info("GoDao quit");
    }
}
