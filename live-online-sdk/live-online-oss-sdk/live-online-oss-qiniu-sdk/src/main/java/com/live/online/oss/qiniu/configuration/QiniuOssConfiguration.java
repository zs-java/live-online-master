package com.live.online.oss.qiniu.configuration;

import com.live.online.oss.qiniu.QiniuOssUtils;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author 朱帅
 * @date 2020-08-16 2:17 下午
 */
public class QiniuOssConfiguration implements DeferredImportSelector {

    @Override
    @SuppressWarnings("all")
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[] {
                QiniuOssUtils.class.getName()
        };
    }
}
