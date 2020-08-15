package com.live.online.common.core.lock.core;

import com.live.online.common.core.lock.exception.DistributedLockException;
import org.springframework.context.ApplicationContext;

import java.util.Objects;

/**
 * <pre/>
 * 分布式锁 {@link DistributedLock} 工具类
 * 提供常用操作的静态工具方法
 * {@link LockUtils#newDistributedLock()}
 * {@link LockUtils#createAndLock(String)}
 * {@link LockUtils#unlock(DistributedLock)}
 * @author 朱帅
 * @date 2020-08-14 12:53 上午
 */
public class LockUtils {

    private static ApplicationContext applicationContext;

    /**
     * 设置 {@link ApplicationContext}，用于从 BeanFactory 中获取多例的锁实例
     * @param applicationContext {@link ApplicationContext}
     */
    public static void setApplicationContext(ApplicationContext applicationContext) {
        LockUtils.applicationContext = applicationContext;
    }

    /**
     * 创建一个分布式锁对象，并返回实例
     * @return {@link DistributedLock}
     */
    public static DistributedLock newDistributedLock() {
        try {
            return applicationContext.getBean(DistributedLock.class);
        } catch (Exception e) {
            throw new DistributedLockException("DistributedLock 示例创建失败，请检查是否开始 @EnableDistributedLock 并指定正确的锁实现", DistributedLock.class, e);
        }
    }

    /**
     * 创建并返回分布式锁示例，且上锁
     * @param lockKey lock id
     * @return {@link DistributedLock}
     */
    public static DistributedLock createAndLock(String lockKey) {
        return newDistributedLock().lock(lockKey);
    }

    /**
     * 释放分布式锁
     * @param lock lock id
     */
    public static void unlock(DistributedLock lock) {
        Objects.requireNonNull(lock).unlock();
    }

}
