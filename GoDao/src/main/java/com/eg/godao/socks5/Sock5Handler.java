package com.eg.godao.socks5;

import com.eg.gocommon.filter.GoFactory;
import com.eg.godao.socks5.dto.ConnectAdr;
import com.eg.godao.socks5.dto.S5Constant;
import com.eg.godao.socks5.dto.S5Stat;
import io.netty.util.NetUtil;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Sock5Handler implements Handler<Buffer> {
    protected volatile int stat;
    protected final String sysId;
    protected final String logId;
    protected final ConnectAdr connectAdr;
    protected final NetSocket localSocket;
    private final Sock5Router sock5Router;
    protected final Vertx vertx;
    public Sock5Handler(String sysId, String logId, NetSocket socket, Sock5Router router,Vertx vertx){
        stat = S5Stat.IDLE.getSt();
        this.sysId = sysId;
        this.logId = logId;
        this.connectAdr = new ConnectAdr();
        this.localSocket = socket;
        this.sock5Router = router;
        this.vertx = vertx;
    }

    protected abstract void transferData(Buffer buffer);
    protected abstract void connect();
    protected abstract void close(String from);

    @Override
    public void handle(Buffer buffer) {
        try {
            if (stat < S5Stat.CONNECTING.getSt()) {
                handleS5Cmd(buffer);
            } else if (stat < S5Stat.TRANSFERRING.getSt()) {
                handleConnect(buffer);
            } else {
                transferData(buffer);
            }
        }catch (Throwable t){
            t.printStackTrace();
            localSocket.close();
        }
    }

    public void closeAll(String from){
        if(stat != S5Stat.CLOSE.getSt()){
            stat = S5Stat.CLOSE.getSt();
            sock5Router.remove(sysId);
            close(from);
            localSocket.close();
        }
    }

    public void pipToLocal(Buffer buffer){
        if(stat != S5Stat.CLOSE.getSt()) {
            localSocket.write(buffer, GoFactory.ofGrpc(logId, ar -> {
                if (ar.failed()) {
                    log.warn("pip to local error:{}", ar.cause().getMessage());
                    closeAll("pipToLocal");
                }
            }));
        }
    }

    private void handleS5Cmd(Buffer buffer){
        log.debug("Stat={} handle, len={}", stat, buffer.length());
        byte ver = buffer.getByte(0);
        if(ver != S5Constant.S5VER){
            log.warn("ver={} !={}", ver, S5Constant.S5VER);
            localSocket.close();
        }else{
            Buffer out = Buffer.buffer().appendByte(S5Constant.S5VER).appendByte(S5Constant.S5_NO_AUTH);
            localSocket.write(out);
            stat = S5Stat.CONNECTING.getSt();
        }
    }

    private void handleConnect(Buffer buffer){
        log.debug("Stat={} handle, len={}", stat, buffer.length());
        try {
            if (getConnectInfo(buffer) != 0) {
                localSocket.close();
                return;
            }
        }catch (Throwable t){
            if(t instanceof IndexOutOfBoundsException) {
                log.info("pause");
                localSocket.pause();
            }else{
                log.warn("handleConnect:{}", t.getMessage());
                localSocket.close();
            }
            return;
        }
        connect();
    }

    private int getConnectInfo(Buffer buffer){
        byte ver = buffer.getByte(0);
        if(ver != S5Constant.S5VER){
            log.warn("ver={} !={}", ver, S5Constant.S5VER);
            return -1;
        }
        byte cmd = buffer.getByte(1);
        if(cmd != S5Constant.S5_CMD){
            log.warn("cmd={} !={} (CONNECT)", cmd, S5Constant.S5_CMD);
            return -1;
        }
        buffer.getByte(2);
        /*远程连接对方**/
        byte atyp = buffer.getByte(3);
        byte[] adr = null;
        short readPos = 4;
        switch (atyp) {
            case S5Constant.ADR_IPV4 -> adr = new byte[4];
            case S5Constant.ADR_DOMAIN -> {
                short len = buffer.getByte(4);
                readPos += 1;
                adr = new byte[len];
            }
            case S5Constant.ADR_IPV6 -> adr = new byte[16];
        }
        if(adr == null){
            log.warn("Go.Id={}, atyp={} is error", logId, atyp);
            return -1;
        }
        if((adr.length + readPos) >= buffer.length()){
            log.warn("out of bound:{} + {} > {}", adr.length, readPos, buffer.length());
            return -1;
        }
        log.debug("address type={}, len={}", atyp, adr.length);
        buffer.getBytes(readPos, (readPos + adr.length), adr);
        short port = buffer.getShort(readPos + adr.length);
        String hostAddress;
        connectAdr.setAtyp(atyp);
        connectAdr.setPort(port);
        if((atyp == S5Constant.ADR_IPV4) || (atyp == S5Constant.ADR_IPV6)){
            hostAddress = NetUtil.bytesToIpAddress(adr);
            connectAdr.setTaddr(adr);
        }else{
            hostAddress = new String(adr);
            byte [] ipaddr = new byte[adr.length + 1];
            ipaddr[0] = (byte)adr.length;
            System.arraycopy(adr, 0, ipaddr, 1, adr.length);
            connectAdr.setTaddr(ipaddr);
        }
        connectAdr.setHost(hostAddress);
        return 0;
    }

    protected void successRsp(){
        Buffer outBuf = Buffer.buffer();
        outBuf.appendByte(S5Constant.S5VER);
        outBuf.appendByte(S5Constant.CONNECT_OK);
        outBuf.appendByte(S5Constant.S5_RSV);
        outBuf.appendByte(connectAdr.getAtyp());
        outBuf.appendBytes(connectAdr.getTaddr());
        outBuf.appendShort(connectAdr.getPort());
        pipToLocal(outBuf);
    }
    protected void failResp(byte error){
        Buffer outBuf = Buffer.buffer().appendByte(S5Constant.S5VER).appendByte(error);
        pipToLocal(outBuf);
    }
}
