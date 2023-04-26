package com.fit.entity.system;
/**
 * 
* 类名称：用户(该类仅共接口使用)
* @author 符景禄
* @version 1.0
 */
public class UserVo {
	
	private String userId;		//用户id
	private String userName;	//用户名
	private String realName;	//姓名
	private String rights;		//权限
	private String roleId;		//角色id
	private String roleName;    //角色名称
	private String lastLogin;	//最后登录时间
	private String status;		//状态
	private Integer level;       //用户级别
	private String businessId;  //业务ID
	private String businessType; //业务类型
	
	private String phone;
	private String tokenExpire;
	
	private String orgCode;//机构编码
	private String belongGroup;//所属分组

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getRights() {
		return rights;
	}
	public void setRights(String rights) {
		this.rights = rights;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTokenExpire() {
		return tokenExpire;
	}
	public void setTokenExpire(String tokenExpire) {
		this.tokenExpire = tokenExpire;
	}
	/**
	 * 用户级别
	 * @author fujl 20167-5-20 ++
	 * */
	public Integer getLEVEL() {
		return level;
	}
	/**
	 * 用户级别
	 * @author fujl 20167-5-20 ++
	 * */
	public void setLEVEL(Integer level) {
		this.level = level;
	}
	
	/**
	 * 业务ID
	 * @author fujl 20167-5-20 ++
	 * */
	public String getBusinessId() {
		return businessId;
	}
	/**
	 * 业务ID
	 * @author fujl 20167-5-20 ++
	 * */
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	
	/**
	 * 业务类型 
	 * @author fujl 20167-5-20 ++
	 * */
	public String getBusinessType() {
		return businessType;
	}
	/**
	 * 业务类型 
	 * @author fujl 20167-5-20 ++
	 * */
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	/**
	 * 机构代码
	 * */
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	/**
	 * 所属分组 
	 **/
	public String getBelongGroup() {
		return belongGroup;
	}
	public void setBelongGroup(String belongGroup) {
		this.belongGroup = belongGroup;
	}
}
