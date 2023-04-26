package com.fit.entity.cache;

import java.sql.Timestamp;

public class TaskLog {
	private String taskId;
	private String taskName;
	private String businessClass;
	private String createId;
	private String taskCron;
	private Timestamp createTime;
	private String status;
	private String remark;
	
    public TaskLog() {
        super();
    }
    
	public TaskLog(String taskId, String taskName, String businessClass, String createId, String taskCron,
			Timestamp createTime, String status, String remark) {
		super();
		this.taskId = taskId;
		this.taskName = taskName;
		this.businessClass = businessClass;
		this.createId = createId;
		this.taskCron = taskCron;
		this.createTime = createTime;
		this.status = status;
		this.remark = remark;
	}


	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getBusinessClass() {
		return businessClass;
	}

	public void setBusinessClass(String businessClass) {
		this.businessClass = businessClass;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getTaskCron() {
		return taskCron;
	}

	public void setTaskCron(String taskCron) {
		this.taskCron = taskCron;
	}

	public Timestamp getCreatetime() {
		return createTime;
	}

	public void setCreatetime(Timestamp createTime) {
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

}
