package com.eg.godao;

import com.eg.gocommon.utils.IdUtil;
import com.eg.godao.socks5.remotehandler.RtCall;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class GoDaoTest {

    @Test
    public void testH2c() throws InterruptedException {
        RtCall rtCall = new RtCall("wogo.com", 8443,"F:\\workspace\\opt\\tls");
        rtCall.start();
        CountDownLatch latch = new CountDownLatch(1);
//        GoDaoFuture<GoResponse> future = RtCall.hello(req);
//
//        future.onComplete(ar->{
//            if(ar.failed()){
//                log.warn("Hello exceptions:>>>>>", ar.getCause());
//            }else{
//                log.info("Hello success:{}", ar.getResult().getData());
//            }
//            latch.countDown();
//        });
        latch.await();
    }

    @Test
    public void testIds(){
        System.out.println(IdUtil.getSysClientId());
    }
}
