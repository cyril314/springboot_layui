package com.fit.entity.system;
/**
 * 员工类
 * @author liguisheng
 *
 */
public class Employee {
	private String ID;		//用户id
	private String NAME;	//用户名
	private String SEX; 	//性别
	private String AGE;		//姓名
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getSEX() {
		return SEX;
	}
	public void setSEX(String sEX) {
		SEX = sEX;
	}
	public String getAGE() {
		return AGE;
	}
	public void setAGE(String aGE) {
		AGE = aGE;
	}
	
}
