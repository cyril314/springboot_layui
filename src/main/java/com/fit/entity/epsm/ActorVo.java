package com.fit.entity.epsm;

public class ActorVo {
	  private String actorid;
	    private String actorcode;
	    private String actorname;
	    private String phone;
	    private String status;
	    private String username;
	    private String password;
	    private String actortype;
	    private String actorrole;
	    private String remark;
	    private String deleteflag;
	    private java.util.Date createtime;
	    private String creatorid;
	    private java.util.Date updatetime;
	    private String updaterid;
	    
	    private String tokenExpire;
	    
	    public ActorVo() {
	        super();
	    }
	    public ActorVo(String actorid,String actorcode,String actorname,String phone,String status,String username,String password,String actortype,String actorrole,String remark,String deleteflag,java.util.Date createtime,String creatorid,java.util.Date updatetime,String updaterid) {
	        super();
	        this.actorid = actorid;
	        this.actorcode = actorcode;
	        this.actorname = actorname;
	        this.phone = phone;
	        this.status = status;
	        this.username = username;
	        this.password = password;
	        this.actortype = actortype;
	        this.actorrole = actorrole;
	        this.remark = remark;
	        this.deleteflag = deleteflag;
	        this.createtime = createtime;
	        this.creatorid = creatorid;
	        this.updatetime = updatetime;
	        this.updaterid = updaterid;
	    }
	    public String getActorid() {
	        return this.actorid;
	    }

	    public void setActorid(String actorid) {
	        this.actorid = actorid;
	    }

	    public String getActorcode() {
	        return this.actorcode;
	    }

	    public void setActorcode(String actorcode) {
	        this.actorcode = actorcode;
	    }

	    public String getActorname() {
	        return this.actorname;
	    }

	    public void setActorname(String actorname) {
	        this.actorname = actorname;
	    }

	    public String getPhone() {
	        return this.phone;
	    }

	    public void setPhone(String phone) {
	        this.phone = phone;
	    }

	    public String getStatus() {
	        return this.status;
	    }

	    public void setStatus(String status) {
	        this.status = status;
	    }

	    public String getUsername() {
	        return this.username;
	    }

	    public void setUsername(String username) {
	        this.username = username;
	    }

	    public String getPassword() {
	        return this.password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

	    public String getActortype() {
	        return this.actortype;
	    }

	    public void setActortype(String actortype) {
	        this.actortype = actortype;
	    }

	    public String getActorrole() {
	        return this.actorrole;
	    }

	    public void setActorrole(String actorrole) {
	        this.actorrole = actorrole;
	    }

	    public String getRemark() {
	        return this.remark;
	    }

	    public void setRemark(String remark) {
	        this.remark = remark;
	    }

	    public String getDeleteflag() {
	        return this.deleteflag;
	    }

	    public void setDeleteflag(String deleteflag) {
	        this.deleteflag = deleteflag;
	    }

	    public java.util.Date getCreatetime() {
	        return this.createtime;
	    }

	    public void setCreatetime(java.util.Date createtime) {
	        this.createtime = createtime;
	    }

	    public String getCreatorid() {
	        return this.creatorid;
	    }

	    public void setCreatorid(String creatorid) {
	        this.creatorid = creatorid;
	    }

	    public java.util.Date getUpdatetime() {
	        return this.updatetime;
	    }

	    public void setUpdatetime(java.util.Date updatetime) {
	        this.updatetime = updatetime;
	    }

	    public String getUpdaterid() {
	        return this.updaterid;
	    }

	    public void setUpdaterid(String updaterid) {
	        this.updaterid = updaterid;
	    }
		public String getTokenExpire() {
			return tokenExpire;
		}
		public void setTokenExpire(String tokenExpire) {
			this.tokenExpire = tokenExpire;
		}
}
