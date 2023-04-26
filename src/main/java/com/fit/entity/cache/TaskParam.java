package com.fit.entity.cache;

import java.sql.Timestamp;

public class TaskParam {
		private String taskParamId;
		private String taskId;
		private String paramCode;
		private String paramValue;
		private Timestamp createTime;
		private String status;
		private String remark;
		
	    public TaskParam(String taskParamId, String taskId, String paramCode,String paramValue, Timestamp createTime, String status,
				String remark) {
			super();
			this.taskParamId = taskParamId;
			this.taskId = taskId;
			this.paramCode = paramCode;
			this.setParamValue(paramValue);
			this.createTime = createTime;
			this.status = status;
			this.remark = remark;
		}

		public TaskParam() {
	        super();
	    }

		public String getTaskParamId() {
			return taskParamId;
		}

		public void setTaskParamId(String taskParamId) {
			this.taskParamId = taskParamId;
		}

		public String getTaskId() {
			return taskId;
		}

		public void setTaskId(String taskId) {
			this.taskId = taskId;
		}

		public String getParamCode() {
			return paramCode;
		}

		public void setParamCode(String paramCode) {
			this.paramCode = paramCode;
		}

		public Timestamp getCreattime() {
			return createTime;
		}

		public void setCreattime(Timestamp createTime) {
			this.createTime = createTime;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public String getParamValue() {
			return paramValue;
		}

		public void setParamValue(String paramValue) {
			this.paramValue = paramValue;
		}
	  
}
