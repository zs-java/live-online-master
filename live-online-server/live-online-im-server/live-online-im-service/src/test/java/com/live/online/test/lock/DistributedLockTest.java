package com.live.online.test.lock;

import com.live.online.common.core.lock.core.DistributedLock;
import com.live.online.common.core.lock.core.LockUtils;
import com.live.online.im.ImServiceApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

/**
 * @author 朱帅
 * @date 2020-08-15 1:05 上午
 */
@SpringBootTest(classes = ImServiceApp.class)
@RunWith(SpringRunner.class)
public class DistributedLockTest {

    private CountDownLatch countDownLatch = null;

    @Test
    public void lockTest() throws Exception {

        Runnable runnable = () -> {
            DistributedLock test = LockUtils.createAndLock("test");
            try {
                System.out.println(Thread.currentThread().getName() + "获得锁,count=" + countDown());
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                LockUtils.unlock(test);
                System.out.println(Thread.currentThread().getName() + "释放锁");
            }
        };

        int count = 10;

        countDownLatch = new CountDownLatch(count);

        for (int i = 0; i < count; i++) {
            new Thread(runnable).start();
        }

        countDownLatch.await();
    }

    private long countDown() {
        countDownLatch.countDown();
        return countDownLatch.getCount();
    }

}
