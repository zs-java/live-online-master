package com.live.online.common.redis.lock;

import com.live.online.common.core.lock.core.EasyDistributedLock;
import com.live.online.common.core.lock.exception.DistributedLockException;
import com.sun.istack.internal.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * TODO 暂未完成
 * @author 朱帅
 * @date 2020-08-14 1:11 上午
 */
@Component
@Scope("prototype")
public class EasyRedisDistributedLock extends SimpleRedisDistributedLock implements EasyDistributedLock {

    private String lockKey;

    public EasyRedisDistributedLock() {
    }

    public EasyRedisDistributedLock(String lockKey) {
        this.lockKey = lockKey;
    }

    @Override
    public void lock(@NotNull String lockKey) {
        this.lockKey = lockKey;
        super.lock(lockKey);
    }

    @Override
    public void unlock(@NotNull String lockKey) {
        this.lockKey = lockKey;
        super.unlock(lockKey);
    }

    @Override
    public void lock() {
        if (StringUtils.isBlank(lockKey)) {
            throw new DistributedLockException("LockKey 不能为空", this.getClass());
        }
        tryLock(lockKey);
    }

    @Override
    public void unlock() {
        if (StringUtils.isBlank(lockKey)) {
            throw new DistributedLockException("LockKey 不能为空", this.getClass());
        }
        unlock(lockKey);
    }

    public String getLockKey() {
        return lockKey;
    }

    public void setLockKey(String lockKey) {
        this.lockKey = lockKey;
    }

}
