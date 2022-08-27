package com.eg.gocommon.grpc;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeaderClientInterceptor implements ClientInterceptor {
    private final static Metadata.Key<String> CID_KEY = Metadata.Key.of("go.cid", Metadata.ASCII_STRING_MARSHALLER);
    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method,
                                                               CallOptions callOptions, Channel next) {
        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {

            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                /* put custom header */
                headers.put(CID_KEY, GoContext.getCid());
                super.start(new ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT>(responseListener) {
                    @Override
                    public void onHeaders(Metadata headers) {
                        super.onHeaders(headers);
                    }
                }, headers);
            }
        };
    }
}
