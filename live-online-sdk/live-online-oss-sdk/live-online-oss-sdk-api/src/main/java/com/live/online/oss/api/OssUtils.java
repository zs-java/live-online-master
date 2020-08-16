package com.live.online.oss.api;

import java.io.File;

/**
 * @author 朱帅
 * @date 2020-08-16 2:09 下午
 */
public interface OssUtils {

    /**
     * 示例方法，oss 文件上传
     * @param file file
     * @param namespace 命名空间
     * @param fileName 文件名
     * @return 文件访问路径
     */
    String upload(File file, String namespace, String fileName);

}
