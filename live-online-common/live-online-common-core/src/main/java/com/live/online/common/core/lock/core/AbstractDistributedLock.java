package com.live.online.common.core.lock.core;

import com.live.online.common.core.lock.exception.DistributedLockException;
import com.sun.istack.internal.NotNull;
import org.apache.commons.lang3.StringUtils;

/**
 * <pre/>
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

    /** 锁id*/
    private String lockKey;

    public AbstractDistributedLock() {
    }

    public AbstractDistributedLock(int maxRetryTime) {
        this.maxRetryTime = maxRetryTime;
    }

    /**
     * <pre/>
     * 获取分布式锁
     * 1. 将 {@link DistributedLock#lock(String)} 拆分为 {@link AbstractDistributedLock#tryLock(String)}、{@link AbstractDistributedLock#waitLock(String)}
     * 2. 封装异常处理
     * 3. 限制最大重试次数
     * @param lockKey key
     * @return {@link DistributedLock}
     */
    @Override
    public DistributedLock lock(@NotNull String lockKey) {
        try {
            this.lockKey = lockKey;
            int retryTime = 0;
            while (!tryLock(lockKey)) {
                if (++retryTime >= maxRetryTime) {
                    throw new DistributedLockException("最大重试次数为" + maxRetryTime+ "，重试次数超出限制！", this.getClass());
                }
                waitLock(lockKey);
            }
            return this;
        } catch (Exception e) {
            if (e instanceof DistributedLockException) {
                throw e;
            } else {
                throw new DistributedLockException(e.getMessage(), this.getClass(), e);
            }
        }
    }

    /**
     * <pre/>
     * 释放分布式锁
     * 封装异常处理
     */
    @Override
    public void unlock() {
        try {

            if (StringUtils.isBlank(this.lockKey)) {
                throw new DistributedLockException("锁释放异常，LockKey IS NULL", this.getClass());
            }

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
     * {@link java.io.Closeable#close()}
     */
    @Override
    public void close() {
        unlock();
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
     * @param lockKey key
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
