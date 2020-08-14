package com.live.online.common.zookeeper.lock;

import com.live.online.common.core.lock.core.AbstractDistributedLock;
import com.live.online.common.core.lock.exception.DistributedLockException;
import com.live.online.common.zookeeper.conf.ZookeeperConfiguration;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.CountDownLatch;

/**
 * <pre/>
 * 基于 Zookeeper 的分布式锁实现
 * @author 朱帅
 * @date 2020-08-14 12:25 上午
 */
@Scope("prototype")
public class SimpleZookeeperDistributeLock extends AbstractDistributedLock {

    private CountDownLatch countDownLatch = null;

    private final ZkClient zkClient;

    public SimpleZookeeperDistributeLock(ZookeeperConfiguration configuration) {
        zkClient = new ZkClient(configuration.getZkUrl());
    }

    @Override
    public boolean tryLock(String lockKey) throws DistributedLockException {
        try {
            // 创建path路径，path不存在则能创建成功，即拿到锁
            zkClient.createEphemeral(lockKey);
            return true;
        } catch (Exception e) {
            // path节点已经存在，捕获异常，即没有拿到锁
            return false;
        }
    }

    @Override
    public void waitLock(String lockKey) throws DistributedLockException {
        IZkDataListener iZkDataListener = new IZkDataListener() {
            @Override
            public void handleDataDeleted(String s) {
                // path节点被删除
                if (countDownLatch != null) {
                    countDownLatch.countDown();
                }
            }

            @Override
            public void handleDataChange(String s, Object o) {
            }
        };
        // zk 注册 DataChange 事件，监听 path 路径改变
        zkClient.subscribeDataChanges(lockKey, iZkDataListener);

        if(zkClient.exists(lockKey)) {
            // path路径已经存在，说明已经有线程获取锁
            countDownLatch = new CountDownLatch(1);
            try {
                // 使用信号量进行等待，当path节点被删除（锁被释放）时，通过zk事件监听，结束线程等待
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new DistributedLockException("等待锁资源异常", this.getClass(), e);
            }
        }

        // 删除监听
        zkClient.unsubscribeDataChanges(lockKey, iZkDataListener);
    }

    @Override
    public void release(String lockKey) throws DistributedLockException {
        zkClient.close();
    }
}
