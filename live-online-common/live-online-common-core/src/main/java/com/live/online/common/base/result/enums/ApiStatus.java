package com.live.online.common.base.result.enums;

/**
 * Api Service 返回状态
 * @author 朱帅
 * @date 2020-08-12 10:58 下午
 */
public enum ApiStatus {

    /** success */
    SUCCESS(200, "success"),
    /** error */
    ERROR(500, "error"),
    /** bad request*/
    BAD_REQUEST(400, "bad request"),
    /** 未找到请求的数据*/
    NOT_FOUND_DATA(404, "data not found"),

    /** 获取用户异常*/
    USER_ERROR(1001, "user error")

    ;

    private final Integer code;
    private final String msg;

    ApiStatus(Integer code, String msg) {
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
