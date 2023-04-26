package com.fit.controller.base;

import java.io.Serializable;
import java.util.Map;

/**
 * @AUTO 统一返回JSON对象
 * @Author AIM
 * @DATE 2019/1/2
 */
public class ResultJson<T> implements Serializable {

    private final static Integer SUCCESS_CODE = 0;

    private Integer code;
    private String msg;
    private T data;

    /**
     * 其他内容
     */
    private Map<String, Object> other;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Map<String, Object> getOther() {
        return other;
    }

    public void setOther(Map<String, Object> other) {
        this.other = other;
    }

    public ResultJson() {
        this.code = SUCCESS_CODE;
        this.msg = "SUCCESS";
    }

    public ResultJson(Integer code, String message) {
        this.code = code;
        this.msg = message;
    }

    public ResultJson(Integer code, String message, T data) {
        this.code = code;
        this.msg = message;
        this.data = data;
    }

    public ResultJson(Integer code, String message, T data, Map<String, Object> other) {
        this.code = code;
        this.msg = message;
        this.data = data;
        this.other = other;
    }

    //快速返回成功
    public static <T> ResultJson success() {
        return new ResultJson<T>(SUCCESS_CODE, "请求成功", null);
    }

    public static <T> ResultJson success(T result) {
        return new ResultJson<T>(SUCCESS_CODE, "请求成功", result);
    }

    public static <T> ResultJson success(String message, T result) {
        return new ResultJson<T>(SUCCESS_CODE, message, result);
    }

    public static <T> ResultJson success(String message, T result, Map<String, Object> extra) {
        return new ResultJson<T>(SUCCESS_CODE, message, result, extra);
    }

    //快速返回失败状态
    public static <T> ResultJson fail() {
        return new ResultJson<T>(CodeEnum.SYSTEM_ERROR.getCode(), CodeEnum.SYSTEM_ERROR.getMessage());
    }

    public static <T> ResultJson fail(T result) {
        return new ResultJson<T>(CodeEnum.SYSTEM_ERROR.getCode(), CodeEnum.SYSTEM_ERROR.getMessage(), result);
    }

    public <T> ResultJson fail(String message, T result) {
        return new ResultJson<T>(CodeEnum.SYSTEM_ERROR.getCode(), message, result);
    }

    public <T> ResultJson fail(String message, T result, Map<String, Object> extra) {
        return new ResultJson<T>(CodeEnum.SYSTEM_ERROR.getCode(), message, result, extra);
    }

    public static <T> ResultJson fail(CodeEnum CodeEnum) {
        return new ResultJson<T>(CodeEnum.getCode(), CodeEnum.getMessage());
    }

    public static <T> ResultJson fail(CodeEnum CodeEnum, T result) {
        return new ResultJson<T>(CodeEnum.getCode(), CodeEnum.getMessage(), result);
    }

    public static <T> ResultJson fail(CodeEnum CodeEnum, String message, T result) {
        return new ResultJson<T>(CodeEnum.getCode(), message, result);
    }

    public static <T> ResultJson fail(CodeEnum CodeEnum, String message, T result, Map<String, Object> extra) {
        return new ResultJson<T>(CodeEnum.getCode(), message, result, extra);
    }

    //快速返回自定义状态码
    public static <T> ResultJson result(Integer code, String message) {
        return new ResultJson<T>(code, message);
    }

    public static <T> ResultJson result(Integer code, String message, T result) {
        return new ResultJson<T>(code, message, result);
    }

    public static <T> ResultJson result(Integer code, String message, T result, Map<String, Object> extra) {
        return new ResultJson<T>(code, message, result, extra);
    }

    /**
     * @AUTO 状态码枚举
     * @DATE 2019/1/2
     */
    enum CodeEnum {
        SYSTEM_ERROR(500, "系统错误"),
        PARAMETER_CHECK_ERROR(400, "参数校验错误"),
        AUTH_VALID_ERROR(701, "用户权限不足"),
        UNLOGIN_ERROR(401, "用户未登录或登录状态超时失效"),;

        private final Integer value;
        private final String message;

        CodeEnum(int value, String message) {
            this.value = value;
            this.message = message;
        }

        public int getValue() {
            return value;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return value.toString();
        }

        public Integer getCode() {
            return value;
        }

        public static CodeEnum getByCode(Integer value) {
            for (CodeEnum _enum : values()) {
                if (_enum.getValue() == value) {
                    return _enum;
                }
            }
            return null;
        }
    }
}
