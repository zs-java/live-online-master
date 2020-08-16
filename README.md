# live-online-master

```
ROOT Project live-online-master 项目结构
+--- live-online-common
    \+--- live-online-common-core
    \+--- live-online-common-redis
    \+--- live-online-common-zookeeper
+--- live-online-gateway
+--- live-online-job
+--- live-online-sdk
    \+--- live-online-live-sdk
        \+--- live-online-live-sdk-api
        \+--- live-online-live-liveqing-sdk
        \+--- live-online-live-tencent-sdk
    \+--- live-online-oss-sdk
        \+--- live-online-oss-sdk-api
        \+--- live-online-oss-qiniu-sdk
+--- live-online-server
    \+--- live-online-user-server
        \+--- live-online-user-api
        \+--- live-online-user-service
        \+--- live-online-user-web
    \+--- live-online-im-server
        \+--- live-online-im-api
        \+--- live-online-im-service
        \+--- live-online-im-web  
```

# 环境搭建
#### LiveQing 流媒体服务器[参考](doc/liveqing/README.md)

# 开发规范

### 类名注释
```
/**
 * 类 名: UserRestController
 * 描 述: 用户 Api Controller 
 * 作 者: 张屹峰
 * 创 建：2020年08月14日
 */
@注解
public class UserRestController
```

### 方法注释
```
    /**
     * 描 述： 根据用户Id获取用户对象
     * 作 者： 张屹峰
     * @param userId 用户Id
     * @return User对象
     */
    private User getUserById(Long userId)
```