package com.live.online.common.core.lock.core;

import com.sun.istack.internal.NotNull;

import java.io.Closeable;

/**
 * <pre/>
 * 分布式锁API接口
 * 提供 lock、unlock 方法
 * @author 朱帅
 * @date 2020-08-13 10:42 下午
 */
public interface DistributedLock extends Closeable {

    /**
     * 获取锁
     * @param lockKey key
     * @return lock
     */
    DistributedLock lock(@NotNull String lockKey);

    /**
     * 释放锁
     */
    void unlock();

}
