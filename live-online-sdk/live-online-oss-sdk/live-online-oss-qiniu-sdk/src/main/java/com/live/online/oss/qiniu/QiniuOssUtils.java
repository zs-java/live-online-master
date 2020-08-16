package com.live.online.oss.qiniu;

import com.live.online.oss.api.OssUtils;

import java.io.File;

/**
 * @author 朱帅
 * @date 2020-08-16 2:13 下午
 */
public class QiniuOssUtils implements OssUtils {


    /**
     * 七牛云上传示例
     * @param file file
     * @param namespace 命名空间
     * @param fileName 文件名
     * @return 访问路径
     */
    @Override
    public String upload(File file, String namespace, String fileName) {
        return namespace + "#" + fileName;
    }


}
