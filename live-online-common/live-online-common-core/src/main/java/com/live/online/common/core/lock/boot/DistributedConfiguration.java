package com.live.online.common.core.lock.boot;

import com.live.online.common.core.lock.annotation.EnableDistributedLock;
import com.live.online.common.core.lock.core.AutoDistributedAspect;
import com.live.online.common.core.lock.core.DistributedLockType;
import com.live.online.common.core.lock.core.LockUtils;
import com.live.online.common.core.lock.exception.DistributedLockException;
import com.sun.istack.internal.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * <pre/>
 * 分布式锁自动装配类
 * 加载 {@link EnableDistributedLock} 配置的分布式锁实现类
 * 初始化 {@link LockUtils} 分布式锁工具
 * 初始化 {@link AutoDistributedAspect} 增强类
 * implClass 优先级高于 {@link DistributedLockType}
 * @author 朱帅
 * @date 2020-08-14 11:03 上午
 */
@Configuration
public class DistributedConfiguration implements DeferredImportSelector, ApplicationContextAware {

    private static final String ATTRIBUTE_TYPE = "type";
    private static final String ATTRIBUTE_IMPL_CLASS = "implClass";
    private static final String ATTRIBUTE_BASE_PACKAGE = "basePackage";

    @Override
    @SuppressWarnings("all")
    public String[] selectImports(AnnotationMetadata annotationMetadata) {

        AnnotationAttributes attributes = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(EnableDistributedLock.class.getName()));
        assert attributes != null;

        DistributedLockType type = attributes.getEnum(ATTRIBUTE_TYPE);
        String implClass = attributes.getString(ATTRIBUTE_IMPL_CLASS);
        String basePackage = attributes.getString(ATTRIBUTE_BASE_PACKAGE);

        // 如 implClass 为空， 则取 type 中预设的实现类
        if (StringUtils.isBlank(implClass)) {
            implClass = type.getClassName();
        }

        // 如 basePackage 为空，则不开启 AOP 增强
        if (StringUtils.isBlank(basePackage)) {
            return new String[] {implClass};
        }

        // 反射修改 AOP 类中的 execution 表达式
        dynamicAspectPointcut(basePackage);

        return new String[] {implClass, AutoDistributedAspect.class.getName()};
    }

    /**
     * <pre/>
     * 反射修改 {@link AutoDistributedAspect#rlAop()} 方法注解 {@link Pointcut#value()} 值
     * 即动态设置 AOP 增强范围
     * @param basePackage aop 增强包路径
     */
    private void dynamicAspectPointcut(@NotNull String basePackage) {
        String execution = "execution(public * " + basePackage + ".*.*(..))";
        Class<AutoDistributedAspect> aspectClass = AutoDistributedAspect.class;
        try {
            Method method = aspectClass.getDeclaredMethod("rlAop");
            Pointcut annotation = method.getAnnotation(Pointcut.class);
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
            Field values = invocationHandler.getClass().getDeclaredField("memberValues");
            values.setAccessible(true);
            @SuppressWarnings("all")
            Map<String, Object> memberValues =(Map<String, Object>) values.get(invocationHandler);
            memberValues.put("value", execution);
        } catch (NoSuchMethodException | IllegalAccessException | NoSuchFieldException e) {
            throw new DistributedLockException("AutoDistributedLock 设置异常，请检查 @EnableDistributeLock#basePackage 是否正确", e);
        }
    }

    @Override
    @SuppressWarnings("all")
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        LockUtils.setApplicationContext(applicationContext);
    }
}
