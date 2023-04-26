package com.fit.entity.epsm;
public class Attachment {
	 	private String attachmentid;
	    private String filename;
	    private String serverpath;
	    private String localpath;
	    private String fileurl;
	    private String filesize;
	    private String filetype;
	    private String attachmenttype;
	    private java.util.Date uploadtime;
	    private String uploadsource;
	    private String buissnessid;
	    private String md5;
	    private String remark;
	    private String deleteflag;
	    private java.util.Date createtime;
	    private String creatorid;
	    private java.util.Date updatetime;
	    private String updaterid;
	    public Attachment() {
	        super();
	    }
	    public Attachment(String attachmentid,String filename,String serverpath,String localpath,String fileurl,String filesize,String filetype,String attachmenttype,java.util.Date uploadtime,String uploadsource,String buissnessid,String md5,String remark,String deleteflag,java.util.Date createtime,String creatorid,java.util.Date updatetime,String updaterid) {
	        super();
	        this.attachmentid = attachmentid;
	        this.filename = filename;
	        this.serverpath = serverpath;
	        this.localpath = localpath;
	        this.fileurl = fileurl;
	        this.filesize = filesize;
	        this.filetype = filetype;
	        this.attachmenttype = attachmenttype;
	        this.uploadtime = uploadtime;
	        this.uploadsource = uploadsource;
	        this.buissnessid = buissnessid;
	        this.md5 = md5;
	        this.remark = remark;
	        this.deleteflag = deleteflag;
	        this.createtime = createtime;
	        this.creatorid = creatorid;
	        this.updatetime = updatetime;
	        this.updaterid = updaterid;
	    }
	    public String getAttachmentid() {
	        return this.attachmentid;
	    }

	    public void setAttachmentid(String attachmentid) {
	        this.attachmentid = attachmentid;
	    }

	    public String getFilename() {
	        return this.filename;
	    }

	    public void setFilename(String filename) {
	        this.filename = filename;
	    }

	    public String getServerpath() {
	        return this.serverpath;
	    }

	    public void setServerpath(String serverpath) {
	        this.serverpath = serverpath;
	    }

	    public String getLocalpath() {
	        return this.localpath;
	    }

	    public void setLocalpath(String localpath) {
	        this.localpath = localpath;
	    }

	    public String getFileurl() {
	        return this.fileurl;
	    }

	    public void setFileurl(String fileurl) {
	        this.fileurl = fileurl;
	    }

	    public String getFilesize() {
	        return this.filesize;
	    }

	    public void setFilesize(String filesize) {
	        this.filesize = filesize;
	    }

	    public String getFiletype() {
	        return this.filetype;
	    }

	    public void setFiletype(String filetype) {
	        this.filetype = filetype;
	    }

	    public String getAttachmenttype() {
	        return this.attachmenttype;
	    }

	    public void setAttachmenttype(String attachmenttype) {
	        this.attachmenttype = attachmenttype;
	    }

	    public java.util.Date getUploadtime() {
	        return this.uploadtime;
	    }

	    public void setUploadtime(java.util.Date uploadtime) {
	        this.uploadtime = uploadtime;
	    }

	    public String getUploadsource() {
	        return this.uploadsource;
	    }

	    public void setUploadsource(String uploadsource) {
	        this.uploadsource = uploadsource;
	    }

	    public String getBuissnessid() {
	        return this.buissnessid;
	    }

	    public void setBuissnessid(String buissnessid) {
	        this.buissnessid = buissnessid;
	    }

	    public String getMd5() {
	        return this.md5;
	    }

	    public void setMd5(String md5) {
	        this.md5 = md5;
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
