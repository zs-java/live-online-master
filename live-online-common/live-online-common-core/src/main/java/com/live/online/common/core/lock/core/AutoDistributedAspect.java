package com.live.online.common.core.lock.core;

import com.live.online.common.core.lock.annotation.AutoDistributedLock;
import com.live.online.common.core.lock.annotation.EnableDistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * <pre/>
 * 分布式锁AOP自动增强类
 * 开启入口：{@link EnableDistributedLock#basePackage()}
 * 对类或方法上开启 {@link AutoDistributedLock#enable()} 的方法进行上锁执行
 * @author 朱帅
 * @date 2020-08-15 4:12 下午
 */
@Aspect
@Slf4j
public class AutoDistributedAspect {

    @Pointcut()
    public void rlAop() {
    }

    @Around("rlAop()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        boolean isLockMethod = isLockMethod(proceedingJoinPoint);

        return isLockMethod ? proceedByLock(proceedingJoinPoint) : proceed(proceedingJoinPoint);

    }

    /**
     * 校验该方法是否需要上锁
     * @param proceedingJoinPoint point
     * @return is need lock
     */
    private boolean isLockMethod(ProceedingJoinPoint proceedingJoinPoint) {
        Class<?> clazz = proceedingJoinPoint.getTarget().getClass();

        AutoDistributedLock classAnn = clazz.getDeclaredAnnotation(AutoDistributedLock.class);

        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        AutoDistributedLock methodAnn = signature.getMethod().getDeclaredAnnotation(AutoDistributedLock.class);

        if (methodAnn != null) {
            return methodAnn.enable();
        }

        return classAnn != null && classAnn.enable();
    }

    /**
     * 获取锁Id，id= ClassName#MethodName
     * @param proceedingJoinPoint point
     * @return lockId
     */
    private String getMethodLockKey(ProceedingJoinPoint proceedingJoinPoint) {
        Class<?> clazz = proceedingJoinPoint.getTarget().getClass();
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        return clazz.getName() + "#" + signature.getMethod().getName();
    }

    /**
     * 上锁并执行
     * @param proceedingJoinPoint point
     * @return return object
     * @throws Throwable 执行异常
     */
    private Object proceedByLock(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try(DistributedLock ignored = LockUtils.createAndLock(getMethodLockKey(proceedingJoinPoint))) {
            return proceed(proceedingJoinPoint);
        }
    }

    /**
     * 执行方法
     * @param proceedingJoinPoint point
     * @return return object
     * @throws Throwable 执行异常
     */
    private Object proceed(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return proceedingJoinPoint.proceed();
    }



}
