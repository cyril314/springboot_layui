package com.fit.entity.epsm;
public class Enosedata {
	 	private String enosedataid;
	    private String enoseserialno;
	    private String dysodiatype;
	    private String dysodiavalue;
	    private String status;
	    private java.util.Date testtime;
	    private java.util.Date createtime;
	    
	    
	    private Enose enose;
	    
	    public Enosedata() {
	        super();
	    }
	    public Enosedata(String enosedataid,String enoseserialno,String dysodiatype,String dysodiavalue,String status,java.util.Date testtime,java.util.Date createtime) {
	        super();
	        this.enosedataid = enosedataid;
	        this.enoseserialno = enoseserialno;
	        this.dysodiatype = dysodiatype;
	        this.dysodiavalue = dysodiavalue;
	        this.status = status;
	        this.testtime = testtime;
	        this.createtime = createtime;
	    }
	    public String getEnoseDataid() {
	        return this.enosedataid;
	    }

	    public void setEnoseDataid(String enosedataid) {
	        this.enosedataid = enosedataid;
	    }

	    public String getEnoseserialno() {
	        return this.enoseserialno;
	    }

	    public void setEnoseserialno(String enoseserialno) {
	        this.enoseserialno = enoseserialno;
	    }

	    public String getDysodiatype() {
	        return this.dysodiatype;
	    }

	    public void setDysodiatype(String dysodiatype) {
	        this.dysodiatype = dysodiatype;
	    }

	    public String getDysodiavalue() {
	        return this.dysodiavalue;
	    }

	    public void setDysodiavalue(String dysodiavalue) {
	        this.dysodiavalue = dysodiavalue;
	    }

	    public java.util.Date getTesttime() {
	        return this.testtime;
	    }

	    public void setTesttime(java.util.Date testtime) {
	        this.testtime = testtime;
	    }

	    public java.util.Date getCreatetime() {
	        return this.createtime;
	    }

	    public void setCreatetime(java.util.Date createtime) {
	        this.createtime = createtime;
	    }
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public Enose getEnose() {
			return enose;
		}
		public void setEnose(Enose enose) {
			this.enose = enose;
		}
}
