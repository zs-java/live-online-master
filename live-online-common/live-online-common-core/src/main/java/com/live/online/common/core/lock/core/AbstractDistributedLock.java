package com.live.online.common.core.lock.core;

import com.live.online.common.core.lock.exception.DistributedLockException;
import com.sun.istack.internal.NotNull;

/**
 * 分布式锁抽象类
 * 功能：
 *  1. 统一处理分布式锁异常
 *  2. 封装 max retry 次数
 *  3. lock 方法抽象为 tryLock、waitLock
 * @author 朱帅
 * @date 2020-08-13 10:47 下午
 */
public abstract class AbstractDistributedLock implements DistributedLock {

    /** 默认最大重试次数*/
    private int maxRetryTime = 100;

    public AbstractDistributedLock() {
    }

    public AbstractDistributedLock(int maxRetryTime) {
        this.maxRetryTime = maxRetryTime;
    }

    @Override
    public void lock(@NotNull String lockKey) {
        try {
            int retryTime = 0;
            while (!tryLock(lockKey)) {
                if (++retryTime >= maxRetryTime) {
                    throw new DistributedLockException("最大重试次数为" + maxRetryTime+ "，重试次数超出限制！", this.getClass());
                }
                waitLock(lockKey);
            }
        } catch (Exception e) {
            if (e instanceof DistributedLockException) {
                throw e;
            } else {
                throw new DistributedLockException(e.getMessage(), this.getClass(), e);
            }
        }
    }

    @Override
    public void unlock(@NotNull String lockKey) {
        try {
            release(lockKey);
        } catch (Exception e) {
            if (e instanceof DistributedLockException) {
                throw e;
            } else {
                throw new DistributedLockException(e.getMessage(), this.getClass(), e);
            }
        }
    }

    /**
     * 尝试获取分布式锁
     * @param lockKey key
     * @return 是否成功
     * @throws DistributedLockException 锁异常
     */
    public abstract boolean tryLock(@NotNull String lockKey) throws DistributedLockException;

    /**
     * 等待获取锁
     * @throws DistributedLockException 锁异常
     */
    public abstract void waitLock(@NotNull String lockKey) throws DistributedLockException;

    /**
     * 释放锁资源
     * @param lockKey key
     * @throws DistributedLockException 锁异常
     */
    public abstract void release(@NotNull String lockKey) throws DistributedLockException;
}
