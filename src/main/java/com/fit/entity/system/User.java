package com.fit.entity.system;

import com.fit.entity.Page;

/**
 * 类名称：用户
 */
public class User {

    private String USER_ID;        //用户id
    private String USERNAME;    //用户名
    private String PASSWORD;    //密码
    private String NAME;        //姓名
    private String RIGHTS;        //权限
    private String ROLE_ID;        //角色id
    private String LAST_LOGIN;    //最后登录时间
    private String IP;            //用户登录ip地址
    private String STATUS;        //状态
    private Role role;            //角色对象
    private Page page;            //分页对象
    private String SKIN;        //皮肤
    private String PASSWORDEXT; //旧密码用于兼容旧系统的明文密码
    private String IDENTIFYNO;  //身份证号
    private String SEX;         //性别
    private Integer LEVEL;       //用户级别
    private String BUISNESSID;  //业务ID
    private String BUINESSTYPE; //业务类型

    public String getSKIN() {
        return SKIN;
    }

    public void setSKIN(String sKIN) {
        SKIN = sKIN;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String uSER_ID) {
        USER_ID = uSER_ID;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String uSERNAME) {
        USERNAME = uSERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String pASSWORD) {
        PASSWORD = pASSWORD;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String nAME) {
        NAME = nAME;
    }

    public String getRIGHTS() {
        return RIGHTS;
    }

    public void setRIGHTS(String rIGHTS) {
        RIGHTS = rIGHTS;
    }

    public String getROLE_ID() {
        return ROLE_ID;
    }

    public void setROLE_ID(String rOLE_ID) {
        ROLE_ID = rOLE_ID;
    }

    public String getLAST_LOGIN() {
        return LAST_LOGIN;
    }

    public void setLAST_LOGIN(String lAST_LOGIN) {
        LAST_LOGIN = lAST_LOGIN;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String iP) {
        IP = iP;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String sTATUS) {
        STATUS = sTATUS;
    }

    /**
     * 旧密码用于兼容旧系统的明文密码
     *
     * @author fujl 20167-5-20 ++
     */
    public String getPASSWORDEXT() {
        return PASSWORDEXT;
    }

    /**
     * 旧密码用于兼容旧系统的明文密码
     *
     * @author fujl 20167-5-20 ++
     */
    public void setPASSWORDEXT(String sPASSWORDEXT) {
        PASSWORDEXT = sPASSWORDEXT;
    }

    /**
     * 身份证号
     *
     * @author fujl 20167-5-20 ++
     */
    public String getIDENTIFYNO() {
        return IDENTIFYNO;
    }

    /**
     * 身份证号
     *
     * @author fujl 20167-5-20 ++
     */
    public void setIDENTIFYNO(String sIDENTIFYNO) {
        IDENTIFYNO = sIDENTIFYNO;
    }

    /**
     * 性别
     *
     * @author fujl 20167-5-20 ++
     */
    public String getSEX() {
        return SEX;
    }

    /**
     * 性别
     *
     * @author fujl 20167-5-20 ++
     */
    public void setSEX(String sSEX) {
        SEX = sSEX;
    }

    /**
     * 用户级别
     *
     * @author fujl 20167-5-20 ++
     */
    public Integer getLEVEL() {
        return LEVEL;
    }

    /**
     * 用户级别
     *
     * @author fujl 20167-5-20 ++
     */
    public void setLEVEL(Integer sLEVEL) {
        LEVEL = sLEVEL;
    }

    /**
     * 业务ID
     *
     * @author fujl 20167-5-20 ++
     */
    public String getBUISNESSID() {
        return BUISNESSID;
    }

    /**
     * 业务ID
     *
     * @author fujl 20167-5-20 ++
     */
    public void setBUISNESSID(String sBUISNESSID) {
        BUISNESSID = sBUISNESSID;
    }

    /**
     * 业务类型
     *
     * @author fujl 20167-5-20 ++
     */
    public String getBUINESSTYPE() {
        return BUINESSTYPE;
    }

    /**
     * 业务类型
     *
     * @author fujl 20167-5-20 ++
     */
    public void setBUINESSTYPE(String sBUINESSTYPE) {
        BUINESSTYPE = sBUINESSTYPE;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Page getPage() {
        if (page == null)
            page = new Page();
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

}
