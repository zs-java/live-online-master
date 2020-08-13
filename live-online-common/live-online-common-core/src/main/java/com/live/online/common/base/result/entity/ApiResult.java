package com.live.online.common.base.result.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.live.online.common.base.exception.ApiResultException;
import com.live.online.common.base.result.enums.ApiStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * RPC 返回数据实体类
 * @author 朱帅
 * @date 2020-08-12 10:20 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResult<T> implements Serializable {

    private Integer code;

    private String msg;

    private T data;

    public T getDataOrElse(T other) {
        return Objects.isNull(data) ? other : data;
    }

    @JsonIgnore
    public T getDataRequireSuccess() throws ApiResultException {
        if (!code.equals(ApiStatus.SUCCESS.code())) {
            throw new ApiResultException(code, msg);
        }
        return data;
    }

    public static ApiResult<?> success() {
        return build(ApiStatus.SUCCESS, null);
    }

    public static <T> ApiResult<T> success(T data) {
        return build(ApiStatus.SUCCESS, data);
    }

    public static <T> ApiResult<T> success(String msg, T data) {
        return build(ApiStatus.SUCCESS.code(), msg, data);
    }

    public static ApiResult<?> error() {
        return build(ApiStatus.ERROR, null);
    }

    public static ApiResult<?> error(String msg) {
        return build(ApiStatus.ERROR.code(), msg, null);
    }

    public static <T> ApiResult<T> error(String msg, T data) {
        return build(ApiStatus.ERROR.code(), msg, data);
    }

    public static <T> ApiResult<T> build(ApiStatus apiStatus, T data) {
        return new ApiResult<>(apiStatus.code(), apiStatus.msg(), data);
    }

    private static <T> ApiResult<T> build(int code, String msg, T data) {
        return new ApiResult<>(code, msg, data);
    }

}
