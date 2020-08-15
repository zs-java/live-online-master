package com.live.online.common.core.lock.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre/>
 * 自动上锁标记
 * 类上：表示该类所有方法都需要上锁
 * 方法上：该方法需要上锁
 * 如：类、方法上都要标记，方法上的优先级最高
 * @author 朱帅
 * @date 2020-08-15 4:14 下午
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoDistributedLock {

    @AliasFor("enable")
    boolean value() default true;

    /**
     * 是否需要加锁
     * @return enable
     */
    @AliasFor("value")
    boolean enable() default true;

}
