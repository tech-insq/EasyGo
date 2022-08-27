package com.eg.goaway.grpc;

import com.eg.gocommon.filter.GoFactory;
import com.eg.gofacade.dto.GoResponse;
import com.eg.gofacade.dto.TalkRequest;
import io.grpc.stub.StreamObserver;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class GoChannel implements AutoCloseable{
    private String cid;
    private int stat;
    private NetSocket socket;
    public GoChannel(String cid, int stat, NetSocket socket) {
        this.cid = cid;
        this.stat = stat;
        this.socket = socket;
    }

    public void bindHandler(StreamObserver<GoResponse> observer, GoAwayService service){
        socket.handler(GoFactory.ofGo(cid, new TalkingHandler(cid, observer, service)));
    }

    public void talkTo(TalkRequest req){
        try {
            Buffer buffer = Buffer.buffer(req.getData().toByteArray());
            socket.write(buffer, GoFactory.ofGrpc(cid, ar -> {
                if (ar.failed()) {
                    log.warn("cid:{} talk to remote failed:{}", cid, ar.cause().getMessage());
                }
            }));
        }catch (Throwable t){

        }
    }

    @Override
    public void close(){
        if(socket != null){
            socket.close();
            socket = null;
        }
    }
}
