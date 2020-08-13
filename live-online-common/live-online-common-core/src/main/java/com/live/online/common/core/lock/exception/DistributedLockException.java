package com.live.online.common.core.lock.exception;

import com.live.online.common.core.lock.core.DistributedLock;
import org.apache.commons.lang3.StringUtils;

/**
 * 分布式锁异常，运行时异常
 * @author 朱帅
 * @date 2020-08-12 10:31 下午
 */
public class DistributedLockException extends RuntimeException {

    private String message;
    private Class<? extends DistributedLock> lockClass;
    private Throwable causeThrowable;

    public DistributedLockException() {
    }

    public DistributedLockException(String message) {
        super(message);
        this.message = message;
    }

    public DistributedLockException(String message, Class<? extends DistributedLock> lockClass) {
        super(message);
        this.message = message;
        this.lockClass = lockClass;
    }

    public DistributedLockException(String message, Class<? extends DistributedLock> lockClass, Throwable throwable) {
        super(throwable);
        this.message = message;
        this.lockClass = lockClass;
        this.setCauseThrowable(throwable);
    }

    public void setCauseThrowable(Throwable throwable) {
        this.causeThrowable = this.getCauseThrowable(throwable);
    }

    private Throwable getCauseThrowable(Throwable t) {
        return t.getCause() == null ? t : this.getCauseThrowable(t.getCause());
    }

    public String getMessage() {
        if (!StringUtils.isBlank(this.message)) {
            return this.message;
        } else {
            return this.causeThrowable != null ? this.causeThrowable.getMessage() : StringUtils.EMPTY;
        }
    }

    public Class<? extends DistributedLock> getLockClass() {
        return lockClass;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setLockClass(Class<? extends DistributedLock> lockClass) {
        this.lockClass = lockClass;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }

    public String toString() {
        return "LockClass:" + this.getLockClass() + ", Message:" + this.getMessage();
    }

}
