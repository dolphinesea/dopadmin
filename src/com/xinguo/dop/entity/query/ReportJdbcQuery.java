package com.xinguo.dop.entity.query;

public class ReportJdbcQuery {

	private String operatorName;
	private String startTime;
	private String endTime;

	private String exporttype;

	public String getExporttype() {
		return exporttype;
	}

	public void setExporttype(String exporttype) {
		this.exporttype = exporttype;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public ReportJdbcQuery() {
	}

	public ReportJdbcQuery(String operatorName, String startTime, String endTime) {
		super();
		this.operatorName = operatorName;
		this.startTime = startTime;
		this.endTime = endTime;
	}
}
