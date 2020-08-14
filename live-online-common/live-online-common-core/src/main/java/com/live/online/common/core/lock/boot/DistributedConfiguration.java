package com.live.online.common.core.lock.boot;

import com.live.online.common.core.lock.annotation.EnableDistributedLock;
import com.live.online.common.core.lock.core.DistributedLockType;
import com.live.online.common.core.lock.core.LockUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * <pre/>
 * 分布式锁自动装配类
 * 加载 {@link EnableDistributedLock} 配置的分布式锁实现类
 * 初始化 {@link LockUtils} 分布式锁工具
 * implClass 优先级高于 {@link DistributedLockType}
 * @author 朱帅
 * @date 2020-08-14 11:03 上午
 */
@Configuration
public class DistributedConfiguration implements DeferredImportSelector, ApplicationContextAware {

    @Override
    @SuppressWarnings("all")
    public String[] selectImports(AnnotationMetadata annotationMetadata) {

        AnnotationAttributes attributes = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(EnableDistributedLock.class.getName()));
        assert attributes != null;

        DistributedLockType type = attributes.getEnum("value");
        String implClass = attributes.getString("implClass");

        if (StringUtils.isBlank(implClass)) {
            implClass = type.getClassName();
        }

        return new String[]{
                implClass
        };
    }

    @Override
    @SuppressWarnings("all")
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        LockUtils.setApplicationContext(applicationContext);
    }
}
