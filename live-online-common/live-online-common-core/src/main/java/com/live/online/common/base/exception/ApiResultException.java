package com.live.online.common.base.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * RPC 接口返回数据异常
 * @author 朱帅
 * @date 2020-08-12 10:31 下午
 */
public class ApiResultException extends Exception {

    private int errCode;
    private String errMsg;
    private Throwable causeThrowable;

    public ApiResultException() {
    }

    public ApiResultException(int errCode, String errMsg) {
        super(errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public ApiResultException(int errCode, Throwable throwable) {
        super(throwable);
        this.errCode = errCode;
        this.setCauseThrowable(throwable);
    }

    public void setCauseThrowable(Throwable throwable) {
        this.causeThrowable = this.getCauseThrowable(throwable);
    }

    private Throwable getCauseThrowable(Throwable t) {
        return t.getCause() == null ? t : this.getCauseThrowable(t.getCause());
    }

    public int getErrCode() {
        return this.errCode;
    }

    public String getErrMsg() {
        if (!StringUtils.isBlank(this.errMsg)) {
            return this.errMsg;
        } else {
            return this.causeThrowable != null ? this.causeThrowable.getMessage() : "";
        }
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String toString() {
        return "ErrCode:" + this.getErrCode() + ", ErrMsg:" + this.getErrMsg();
    }

}
