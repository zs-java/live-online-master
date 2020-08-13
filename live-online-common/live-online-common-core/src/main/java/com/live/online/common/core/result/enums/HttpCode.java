package com.live.online.common.core.result.enums;

/**
 * @author 朱帅
 */
public enum HttpCode {

    /**
     * 200请求成功
     */
    SUCCESS(200, "success"),
    /**
     * 207频繁操作
     */
    MULTI_STATUS(207, "频繁操作"),
    /**
     * 400请求参数出错
     */
    BAD_REQUEST(400, "参数错误"),
    /**
     * 401没有登录
     */
    UNAUTHORIZED(401, "没有登录"),
    /**
     * 402登录失败
     */
    LOGIN_FAIL(402, "登录失败"),
    /**
     * 403没有权限
     */
    FORBIDDEN(403, "无权访问"),
    /**
     * 404找不到页面
     */
    NOT_FOUND(404, "找不到页面"),
    /**
     * 405请求方法不能被用于请求相应的资源
     */
    METHOD_NOT_ALLOWED(405, "请求方法不能被用于请求相应的资源"),
    /**
     * 406内容特性不满足
     */
    NOT_ACCEPTABLE(406, "内容特性不满足"),
    /**
     * 408请求超时
     */
    REQUEST_TIMEOUT(408, "请求超时"),
    /**
     * 409发生冲突
     */
    CONFLICT(409, "发生冲突"),
    /**
     * 410已被删除
     */
    GONE(410, "资源已被删除"),
    /**
     * 411没有定义长度
     */
    LENGTH_REQUIRED(411, "没有定义长度"),
    /**
     * 412条件不满足
     */
    PRECONDITION_FAILED(412, "条件不满足"),
    /**
     * 413数据太大
     */
    ENTITY_TOO_LARGE(413, "数据太大"),
    /**
     * 415不是服务器中所支持的格式
     */
    UNSUPPORTED_MEDIA_TYPE(415, "不是服务器中所支持的格式"),
    /**
     * 421连接数过多
     */
    TOO_MANY_CONNECTIONS(421, "连接数过多"),
    /**
     * 423已被锁定
     */
    LOCKED(423, "已被锁定"),
    /**
     * 451法律不允许
     */
    UNAVAILABLE_LEGAL(451, "法律不允许"),
    /**
     * 500服务器出错
     */
    ERROR(500, "服务器出错"),
    /**
     * 501不支持当前请求所需要的某个功能
     */
    NOT_IMPLEMENTED(501, "不支持当前请求所需要的某个功能"),
    /**
     * 503服务器升级中,暂时不可用
     */
    SERVICE_UNAVAILABLE(503, "服务器升级中,暂时不可用"),
    /**
     * 501获取资源所需要的策略并没有被满足
     */
    NOT_EXTENDED(510, "获取资源所需要的策略并没有被满足");

    private final Integer code;
    private final String msg;

    HttpCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer code() {
        return code;
    }

    public String msg() {
        return msg;
    }

    public String toString() {
        return this.code.toString();
    }

}
