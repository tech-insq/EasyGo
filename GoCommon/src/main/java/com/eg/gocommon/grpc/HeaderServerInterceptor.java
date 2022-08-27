package com.eg.gocommon.grpc;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeaderServerInterceptor implements ServerInterceptor {
    private final static Metadata.Key<String> CID_KEY = Metadata.Key.of("go.cid", Metadata.ASCII_STRING_MARSHALLER);
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, final Metadata reqHeaders,
            ServerCallHandler<ReqT, RespT> next) {
        String cid = reqHeaders.get(CID_KEY);
        GoContext.setCid(cid);
        return next.startCall(new ForwardingServerCall.SimpleForwardingServerCall<>(call) {
            @Override
            public void sendHeaders(Metadata responseHeaders) {
                responseHeaders.put(CID_KEY, cid);
                super.sendHeaders(responseHeaders);
            }
        }, reqHeaders);
    }
}
