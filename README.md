# live-online-master

```
ROOT Project live-online-master 项目结构
+--- live-online-common
    \+--- live-online-common-core
    \+--- live-online-common-doc
+--- live-online-eureka-server
+--- live-online-gateway
+--- live-online-job
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