package com.live.online.common.core.lock.annotation;

import com.live.online.common.core.lock.boot.DistributedConfiguration;
import com.live.online.common.core.lock.core.DistributedLockType;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * <pre/>
 * 分布式锁功能启动入口
 * 默认使用 Redis 实现
 * implClass 优先级高于 type
 * @author 朱帅
 * @date 2020-08-14 11:03 上午
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DistributedConfiguration.class)
public @interface EnableDistributedLock {

    String EMPTY = "";

    @AliasFor("type")
    DistributedLockType value() default DistributedLockType.REDIS;

    /**
     * 分布式锁实现类型
     * @return {@link DistributedLockType}
     */
    @AliasFor("value")
    DistributedLockType type() default DistributedLockType.REDIS;

    /**
     * 分布式锁实现类全路径（优先级高于 type）
     * @return class reference
     */
    String implClass() default EMPTY;

    /**
     * AOP 增强包路径，EMPTY 则不开启 AOP 增强
     * @return package
     */
    String basePackage() default EMPTY;



}
