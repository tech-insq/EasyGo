package com.eg.godao.socks5.localhandler;

import com.eg.gocommon.filter.GoFactory;
import com.eg.godao.socks5.*;
import com.eg.godao.socks5.dto.S5Constant;
import com.eg.godao.socks5.dto.S5Stat;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;
import io.vertx.core.net.SocketAddress;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GoHandler extends Sock5Handler {
    private NetSocket remoteSock;
    public GoHandler(String sysId, String logId, NetSocket socket, Sock5Router router,Vertx vertx){
        super(sysId, logId, socket, router, vertx);
    }

    @Override
    protected void connect(){
        NetClient netClient = vertx.createNetClient();
        SocketAddress inetSocketAddress = SocketAddress.inetSocketAddress(connectAdr.getPort(), connectAdr.getHost());
        netClient.connect(inetSocketAddress, GoFactory.ofConnect(logId, rs->{
            if(rs.failed()){
                log.info("Go.Id={}, connect to={}:{} failed, exceptions:{}", logId, connectAdr.getHost(),
                        connectAdr.getPort(), rs.cause());
                failResp(S5Constant.CONNECT_REJ);
            }else{
                log.debug("Go.Id={}, connect to={}:{} success", logId, connectAdr.getHost(), connectAdr.getPort());
                remoteSock = rs.result();
                successRsp();
                stat = S5Stat.TRANSFERRING.getSt();
                remoteSock.handler(GoFactory.ofGo(logId, new GoLocalHandler(localSocket)));
                remoteSock.exceptionHandler(GoFactory.ofExe(logId, new RtEHandler(logId, this)));
                remoteSock.closeHandler(GoFactory.ofClose(logId, new RtCloseHandler(logId, this)));
            }
        }));
    }

    @Override
    protected void transferData(Buffer buffer){
        remoteSock.write(buffer);
    }

    @Override
    public void close(String from){
        if(remoteSock != null) {
            remoteSock.close();
        }
    }

}
