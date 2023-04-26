package com.fit.entity.system;

/**
 * @AUTO 反向生成库
 * @Author AIM
 * @DATE 2019/1/23
 */
public class RecreateCode {

    /**
     * 属性名
     */
    private String PROPERTYNAME;
    /**
     * 类型名
     */
    private String TYPENAME;
    /**
     * 长度
     */
    private String EXTENT;
    /**
     * 小数点
     */
    private String DECIMALPOINT;
    /**
     * 备注
     */
    private String BZ;
    /**
     * 前台录入
     */
    private String ISFRONT;
    /**
     * 默认值
     */
    private String DEFAULTVALUE;

    public String getPROPERTYNAME() {
        return PROPERTYNAME;
    }

    public void setPROPERTYNAME(String PROPERTYNAME) {
        this.PROPERTYNAME = PROPERTYNAME;
    }

    public String getTYPENAME() {
        return TYPENAME;
    }

    public void setTYPENAME(String TYPENAME) {
        this.TYPENAME = TYPENAME;
    }

    public String getEXTENT() {
        return EXTENT;
    }

    public void setEXTENT(String EXTENT) {
        this.EXTENT = EXTENT;
    }

    public String getDECIMALPOINT() {
        return DECIMALPOINT;
    }

    public void setDECIMALPOINT(String DECIMALPOINT) {
        this.DECIMALPOINT = DECIMALPOINT;
    }

    public String getBZ() {
        return BZ;
    }

    public void setBZ(String BZ) {
        this.BZ = BZ;
    }

    public String getISFRONT() {
        return ISFRONT;
    }

    public void setISFRONT(String ISFRONT) {
        this.ISFRONT = ISFRONT;
    }

    public String getDEFAULTVALUE() {
        return DEFAULTVALUE;
    }

    public void setDEFAULTVALUE(String DEFAULTVALUE) {
        this.DEFAULTVALUE = DEFAULTVALUE;
    }

    @Override
    public String toString() {
        return "{" +
                "PROPERTYNAME='" + PROPERTYNAME + '\'' +
                ", TYPENAME='" + TYPENAME + '\'' +
                ", EXTENT='" + EXTENT + '\'' +
                ", DECIMALPOINT='" + DECIMALPOINT + '\'' +
                ", BZ='" + BZ + '\'' +
                ", ISFRONT='" + ISFRONT + '\'' +
                ", DEFAULTVALUE='" + DEFAULTVALUE + '\'' +
                '}';
    }
}
