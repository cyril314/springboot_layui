package com.fit.util.enums;

//电子鼻数据下载编码
public enum EnoseDataEnum {
	SUCCESS(0, "通信正常"),
    NO_RESPONSE(1, "通信无响应"),
    DATA_ERROR(2, "通信数据缺少"),
    DECODE_ERROR(3, "解码错误"),
    ;
    private Integer code;
    private String msg;
    
    EnoseDataEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public Integer getCode() {
        return code;
    }
    public String getMsg() { return msg; }

    public void setCode(Integer code) {
        this.code = code;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
