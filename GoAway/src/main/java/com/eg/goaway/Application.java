package com.eg.goaway;

import com.eg.goaway.grpc.GoAwayService;
import com.eg.gocommon.grpc.HeaderServerInterceptor;
import io.grpc.*;
import io.grpc.internal.GrpcUtil;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PreDestroy;
import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {
    private Server grpcServer;

    public static void main(String args[]){
        System.setProperty("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.SLF4JLogDelegateFactory");
        SpringApplication.run(Application.class, args);
    }

    @PreDestroy
    public void destroy(){
        if(grpcServer != null){
            try {
                grpcServer.shutdown().awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
        }
    }

    @Override
    public void run(String... args) throws Exception {
        VertxOptions options = new VertxOptions();
        options.setEventLoopPoolSize(8);
        options.setWorkerPoolSize(40);
        Vertx vertx = Vertx.vertx(options);
        TlsServerCredentials.Builder tlsBuilder = TlsServerCredentials.newBuilder()
                .keyManager(new File("./tls/sv1.pem"), new File("./tls/sv1.key"));
        /**客户端需要**/
        tlsBuilder.trustManager(new File("./tls/ca.pem"));
        tlsBuilder.clientAuth(TlsServerCredentials.ClientAuth.REQUIRE);
        ServerCredentials creeds = tlsBuilder.build();
        grpcServer = Grpc.newServerBuilderForPort(8443, creeds)
                .executor(Executors.newFixedThreadPool(40, GrpcUtil.getThreadFactory("grpc-go-%d", true)))
                .addService(ServerInterceptors.intercept(new GoAwayService(vertx), new HeaderServerInterceptor()))
                .build()
                .start();
        log.info("Grpc is started");
        grpcServer.awaitTermination();
    }
}
