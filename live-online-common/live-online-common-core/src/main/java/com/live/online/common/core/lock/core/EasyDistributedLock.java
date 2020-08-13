package com.live.online.common.core.lock.core;

/**
 * TODO 暂未完成
 * @author 朱帅
 * @date 2020-08-14 1:02 上午
 */
public interface EasyDistributedLock extends DistributedLock {

    void lock();

    void unlock();

}
