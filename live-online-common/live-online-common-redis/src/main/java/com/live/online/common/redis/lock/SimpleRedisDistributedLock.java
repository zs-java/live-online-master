package com.live.online.common.redis.lock;

import com.live.online.common.core.lock.core.AbstractDistributedLock;
import com.live.online.common.core.lock.exception.DistributedLockException;
import com.sun.istack.internal.NotNull;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Objects;

/**
 * <pre/>
 * 基于 Redis 的分布式实现
 * @author 朱帅
 * @date 2020-08-13 11:19 下午
 */
@Scope("prototype")
public class SimpleRedisDistributedLock extends AbstractDistributedLock {

    private static final long NX_TIMEOUT = 60;

    private final StringRedisSerializer serializer = new StringRedisSerializer();

    private final RedisTemplate<String, Object> redisTemplate;

    public SimpleRedisDistributedLock(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean tryLock(@NotNull String lockKey) throws DistributedLockException {
        Boolean lock = redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {

            byte[] keyByte = serializer.serialize(lockKey);
            byte[] valByte = serializer.serialize("lock");
            Boolean result = redisConnection.setNX(Objects.requireNonNull(keyByte), Objects.requireNonNull(valByte));

            if (result != null && result) {
                // 设置过期时间防止死锁
                redisConnection.expire(keyByte, NX_TIMEOUT);
            }

            return result == null ? false : result;
        });
        return lock == null ? false : lock;
    }

    @Override
    public void waitLock(@NotNull String lockKey) throws DistributedLockException {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new DistributedLockException("等待锁资源发生异常", this.getClass(), e);
        }
    }

    @Override
    public void release(@NotNull String lockKey) {
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.delete(lockKey);
    }
}
