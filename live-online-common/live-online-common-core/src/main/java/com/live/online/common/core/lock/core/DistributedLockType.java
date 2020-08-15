package com.live.online.common.core.lock.core;

/**
 * <pre/>
 * 分布式锁实现枚举类
 * 提供系统已实现的分布式锁实现类
 * @author 朱帅
 * @date 2020-08-14 10:54 上午
 */
public enum DistributedLockType {

    /**
     * Redis 分布式锁实现类
     */
    REDIS("com.live.online.common.redis.lock.SimpleRedisDistributedLock"),

    /**
     * Zookeeper 分布式锁实现类
     */
    ZOOKEEPER("com.live.online.common.zookeeper.lock.SimpleZookeeperDistributeLock")
    ;

    private final String className;

    DistributedLockType(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
