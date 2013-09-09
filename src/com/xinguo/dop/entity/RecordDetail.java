package com.xinguo.dop.entity;

import java.sql.Time;
import java.sql.Timestamp;

public class RecordDetail {
	private Integer id;

	private String operatorDesk;
	private Timestamp startdate;
	private Timestamp starttime;
	private String type;
	private String answer;
	private String numbertype;
	private Time checknumbertime;
	private String checknumber;
	private String dealproblem;
	private Integer serviceterms;
	private Integer isbad;
	private String operatorName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOperatorDesk() {
		return operatorDesk;
	}

	public void setOperatorDesk(String operatorDesk) {
		this.operatorDesk = operatorDesk;
	}

	public Timestamp getStartdate() {
		return startdate;
	}

	public void setStartdate(Timestamp startdate) {
		this.startdate = startdate;
	}

	public Timestamp getStarttime() {
		return starttime;
	}

	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getNumbertype() {
		return numbertype;
	}

	public void setNumbertype(String numbertype) {
		this.numbertype = numbertype;
	}

	public Time getChecknumbertime() {
		return checknumbertime;
	}

	public void setChecknumbertime(Time checknumbertime) {
		this.checknumbertime = checknumbertime;
	}

	public String getChecknumber() {
		return checknumber;
	}

	public void setChecknumber(String checknumber) {
		this.checknumber = checknumber;
	}

	public String getDealproblem() {
		return dealproblem;
	}

	public void setDealproblem(String dealproblem) {
		this.dealproblem = dealproblem;
	}

	public Integer getServiceterms() {
		return serviceterms;
	}

	public void setServiceterms(Integer serviceterms) {
		this.serviceterms = serviceterms;
	}

	public Integer getIsbad() {
		return isbad;
	}

	public void setIsbad(Integer isbad) {
		this.isbad = isbad;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

}
