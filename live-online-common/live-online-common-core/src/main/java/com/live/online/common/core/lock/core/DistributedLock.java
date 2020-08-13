package com.live.online.common.core.lock.core;

import com.sun.istack.internal.NotNull;

/**
 * 分布式锁API接口
 * @author 朱帅
 * @date 2020-08-13 10:42 下午
 */
public interface DistributedLock {

    /**
     * 获取锁
     * @param lockKey key
     */
    void lock(@NotNull String lockKey);

    /**
     * 释放锁
     * @param lockKey key
     */
    void unlock(@NotNull String lockKey);

}
