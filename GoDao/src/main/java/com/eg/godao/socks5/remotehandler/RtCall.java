package com.eg.godao.socks5.remotehandler;


import com.eg.gocommon.grpc.GoContext;
import com.eg.gocommon.grpc.HeaderClientInterceptor;
import com.eg.gofacade.dto.*;
import io.grpc.*;
import io.grpc.stub.StreamObserver;
import io.vertx.core.Handler;
import lombok.extern.slf4j.Slf4j;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class RtCall implements AutoCloseable{
    private static final int MAX_CHANNEL_SIZE = 32;
    private static volatile RtCall RT_CALL = null;
    private final AtomicInteger robinSelector;
    private final String host;
    private final int port;
    private final String certPath;
    private ArrayList<ManagedChannel> channelList;

    private ArrayList<GoServiceGrpc.GoServiceStub> stubsList;
    public RtCall(String host, int port, String certPath) {
        this.robinSelector = new AtomicInteger(0);
        this.host = host;
        this.port = port;
        this.certPath = certPath;
        this.channelList = new ArrayList<>(MAX_CHANNEL_SIZE);
        this.stubsList = new ArrayList<>(MAX_CHANNEL_SIZE);
    }

    public void start(){
        try {
            TlsChannelCredentials.Builder tlsBuilder = TlsChannelCredentials.newBuilder();
            File certFile = new File(certPath, "cv1.pem");
            File keyFile = new File(certPath, "cv1.key");
            File caFile  = new File(certPath, "ca.pem");
            tlsBuilder.keyManager(certFile, keyFile);
            tlsBuilder.trustManager(caFile);
            ClientInterceptor interceptor = new HeaderClientInterceptor();
            for(int i = 0; i < MAX_CHANNEL_SIZE; i++) {
                ManagedChannel managedChannel = Grpc.newChannelBuilderForAddress(host, port,
                        tlsBuilder.build()).keepAliveWithoutCalls(true).build();
                Channel channel = ClientInterceptors.intercept(managedChannel, interceptor);
                GoServiceGrpc.GoServiceStub stub = GoServiceGrpc.newStub(channel);
                channelList.add(managedChannel);
                stubsList.add(stub);
            }
            RtCall.RT_CALL = this;
        }catch (Throwable t){
            throw new RuntimeException(t);
        }
    }

    /***
     *
     * @param request
     * @param handler
     */
    public static void hello(HelloRequest request, Handler<GoAsyncResult> handler) {
        GoSSObserver observer = new GoSSObserver(handler);
        GoContext.setCid(request.getCid());
        RT_CALL.selector().hello(request, observer);
    }

    /***
     *
     * @param request
     * @param handler
     */
    public static void ask(AskRequest request, Handler<GoAsyncResult> handler) {
        GoContext.setCid(request.getCid());
        GoSSObserver observer = new GoSSObserver(handler);
        RT_CALL.selector().ask(request, observer);
    }


    public static StreamObserver<TalkRequest> talk(StreamObserver<GoResponse> observer) {
        return RT_CALL.selector().talk(observer);
    }

    /***
     *
     * @param request
     * @param handler
     */
    public static void bye(ByeRequest request, Handler<GoAsyncResult> handler) {
        GoContext.setCid(request.getCid());
        GoSSObserver observer = new GoSSObserver(handler);
        RT_CALL.selector().bye(request, observer);
    }

    private GoServiceGrpc.GoServiceStub selector(){
        int selector = robinSelector.incrementAndGet() % MAX_CHANNEL_SIZE;
        if(robinSelector.get() >= 100000){
            robinSelector.set(0);
        }
        return stubsList.get(selector);
    }

    @Override
    public void close(){
        for(ManagedChannel mcl:channelList){
            mcl.shutdown();
        }
    }
}
