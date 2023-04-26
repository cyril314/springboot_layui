package com.fit.util.enums;

public enum SMSTemplateEnum {
	
	REGISTER("1", "注册账号"),
	FORGET("2", "忘记密码"),;
	
    private String templateId;
    private String msg;
    
    SMSTemplateEnum(String templateId, String msg) {
        this.templateId = templateId;
        this.msg = msg;
    }
    public String getTemplateId() {
        return templateId;
    }
    public String getMsg() { return msg; }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
