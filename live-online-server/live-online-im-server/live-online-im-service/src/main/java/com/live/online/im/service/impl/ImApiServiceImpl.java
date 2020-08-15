package com.live.online.im.service.impl;

import com.live.online.common.core.lock.annotation.AutoDistributedLock;
import com.live.online.common.core.result.entity.ApiResult;
import com.live.online.im.api.service.IImApiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;

/**
 * @author 朱帅
 * @date 2020-08-10 1:15 上午
 */
@RestController
public class ImApiServiceImpl implements IImApiService {

    @Value("${im.message:}")
    private String message;

    @GetMapping("/test")
    public ApiResult<String> test() {
        return ApiResult.success("Config:" + message);
    }

    @AutoDistributedLock
    public void lockedMethod(CountDownLatch countDownLatch) throws InterruptedException {
        countDownLatch.countDown();
        System.out.println(Thread.currentThread().getName() + "线程获得锁,count=" + countDownLatch.getCount());
        Thread.sleep(2000);
    }

}
