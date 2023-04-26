package com.fit.entity.epsm;

public class Industry {
		private String industryid;
	    private String industrycode;
	    private String name;
	    private String remark;
	    private String deleteflag;
	    private java.util.Date createtime;
	    private String creatorid;
	    private java.util.Date updatetime;
	    private String updaterid;
	    public Industry() {
	        super();
	    }
	    public Industry(String industryid,String industrycode,String name,String remark,String deleteflag,java.util.Date createtime,String creatorid,java.util.Date updatetime,String updaterid) {
	        super();
	        this.industryid = industryid;
	        this.industrycode = industrycode;
	        this.name = name;
	        this.remark = remark;
	        this.deleteflag = deleteflag;
	        this.createtime = createtime;
	        this.creatorid = creatorid;
	        this.updatetime = updatetime;
	        this.updaterid = updaterid;
	    }
	    public String getIndustryid() {
	        return this.industryid;
	    }

	    public void setIndustryid(String industryid) {
	        this.industryid = industryid;
	    }

	    public String getIndustrycode() {
	        return this.industrycode;
	    }

	    public void setIndustrycode(String industrycode) {
	        this.industrycode = industrycode;
	    }

	    public String getName() {
	        return this.name;
	    }

	    public void setName(String name) {
	        this.name = name;
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
}
