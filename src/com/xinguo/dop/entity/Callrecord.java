package com.xinguo.dop.entity;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * Callrecord entity. @author MyEclipse Persistence Tools
 */

public class Callrecord implements java.io.Serializable {

	// Fields

	private Integer recordid;
	private Integer ticketid;
	private Timestamp recordstarttime;
	private Timestamp recordstoptime;
	private Time recordlength;
	private String savepath;
	private String savefilename;
	private Integer recordcorrect;
	
	private Integer type;

	private String operatorName;
	
	private String mutilid; // 多个ID 逗号分割 用于多个删除
	// Constructors

	public String getOperatorName() {
		return operatorName;
	}



	public String getMutilid() {
		return mutilid;
	}



	public void setMutilid(String mutilid) {
		this.mutilid = mutilid;
	}



	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	/** default constructor */
	public Callrecord() {
	}

	/** full constructor */
	public Callrecord(Integer ticketid, Timestamp recordstarttime,
			Timestamp recordstoptime, Time recordlength, String savepath,
			String savefilename, Integer recordcorrect) {
		this.ticketid = ticketid;
		this.recordstarttime = recordstarttime;
		this.recordstoptime = recordstoptime;
		this.recordlength = recordlength;
		this.savepath = savepath;
		this.savefilename = savefilename;
		this.recordcorrect = recordcorrect;
	}

	// Property accessors

	public Integer getRecordid() {
		return this.recordid;
	}

	public void setRecordid(Integer recordid) {
		this.recordid = recordid;
	}

	public Integer getTicketid() {
		return this.ticketid;
	}

	public void setTicketid(Integer ticketid) {
		this.ticketid = ticketid;
	}

	public Timestamp getRecordstarttime() {
		return this.recordstarttime;
	}

	public void setRecordstarttime(Timestamp recordstarttime) {
		this.recordstarttime = recordstarttime;
	}

	public Timestamp getRecordstoptime() {
		return this.recordstoptime;
	}

	public void setRecordstoptime(Timestamp recordstoptime) {
		this.recordstoptime = recordstoptime;
	}

	public Time getRecordlength() {
		return this.recordlength;
	}

	public void setRecordlength(Time recordlength) {
		this.recordlength = recordlength;
	}

	public String getSavepath() {
		return this.savepath;
	}

	public void setSavepath(String savepath) {
		this.savepath = savepath;
	}

	public String getSavefilename() {
		return this.savefilename;
	}

	public void setSavefilename(String savefilename) {
		this.savefilename = savefilename;
	}

	public Integer getRecordcorrect() {
		return this.recordcorrect;
	}

	public void setRecordcorrect(Integer recordcorrect) {
		this.recordcorrect = recordcorrect;
	}

}