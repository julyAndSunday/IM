package com.github.common.common;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-08-23 21:34
 **/
public class Result<T> {

    public int code;
    public String message;
    public T data;

    public Result() {
    }

    public Result(T date) {
        this.data = date;
    }

    public Result(int code, String message, T date) {
        this.code = code;
        this.message = message;
        this.data = date;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    public static <T> Result<T> filed(T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.FAILED.getMessage(), data);
    }

    /**
     * 未登录返回结果
     */
    public static <T> Result<T> unauthorized(T data) {
        return new Result<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> Result<T> forbidden(T data) {
        return new Result<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
