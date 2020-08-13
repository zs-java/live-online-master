package com.live.online.common.core.lock.core;

import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * TODO 暂未完成
 * @author 朱帅
 * @date 2020-08-14 12:53 上午
 */
public class LockFactory {

    @Autowired
    private ApplicationContext applicationContext;

    public DistributedLock newDistributedLock() {
        return applicationContext.getBean(DistributedLock.class);
    }

    public EasyDistributedLock newEasyDistributedLock() {

        return null;
    }

    public EasyDistributedLock lock(@NotNull String lockKey) {
        EasyDistributedLock lock = (EasyDistributedLock) applicationContext.getBean("classname", lockKey);
        lock.lock();
        return lock;
    }

}
