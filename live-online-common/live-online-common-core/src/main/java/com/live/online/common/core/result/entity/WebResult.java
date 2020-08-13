package com.live.online.common.core.result.entity;

import com.live.online.common.core.result.enums.ApiStatus;
import com.live.online.common.core.result.enums.HttpCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Web 接口返回数据实体类
 * @author 朱帅
 * @date 2020-08-12 11:10 下午
 */
@NoArgsConstructor
public class WebResult<T> implements Serializable {

    @Getter
    @Setter
    private Integer code;

    @Getter
    @Setter
    private String msg;

    @Getter
    @Setter
    private T data;

    public WebResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static WebResult<?> success() {
        return build(HttpCode.SUCCESS, null);
    }

    public static <T> WebResult<T> success(T data) {
        return build(HttpCode.SUCCESS, data);
    }

    public static <T> WebResult<T> success(String msg, T data) {
        return build(ApiStatus.SUCCESS.code(), msg, data);
    }

    public static WebResult<?> error() {
        return build(HttpCode.ERROR, null);
    }

    public static WebResult<?> error(String msg) {
        return build(HttpCode.ERROR.code(), msg, null);
    }

    public static <T> WebResult<T> error(String msg, T data) {
        return build(HttpCode.ERROR.code(), msg, data);
    }

    public static WebResult<?> unauthorized() {
        return build(HttpCode.UNAUTHORIZED, null);
    }

    public static WebResult<?> forbidden() {
        return build(HttpCode.FORBIDDEN, null);
    }

    public static <T> WebResult<T> build(HttpCode httpCode, T data) {
        return new WebResult<>(httpCode.code(), httpCode.msg(), data);
    }

    public static <T> WebResult<T> build(int code, String msg, T data) {
        return new WebResult<>(code, msg, data);
    }

    public long getTime() {
        return System.currentTimeMillis();
    }

}
