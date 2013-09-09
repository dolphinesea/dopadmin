package com.xinguo.dop.entity;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

@SuppressWarnings("serial")
public class CallTicket implements java.io.Serializable {
	private long sequenceNumber = 0;
	private String callingNumber = null;
	private String calledNumber = null;
	private int operatorDesk = 0;
	private String operatorName = null;
	private int originator = 0;
	private String callType = null;
	private int callPriority = 0;
	private Date startTime;
	private Date answerTime;
	private Timestamp establishTime;
	private Timestamp stopTime;
	private String detail = null;

	private Integer ticketid;
	private Integer type = 0;
	private Integer answer = 0;
	private Integer numbertype = 0;
	private Integer checknumber = 0;
	private Time checknumbertime;
	private Integer dealproblem = 0;
	private Integer isbad = 0;
	private Integer serviceterms = 0;
	private Timestamp checktime;

	private Date answerLength;
	
	private String showTime;
	private String result;
	private int recordCount;
	private String reviewer;
	
	private String savefilename;
	
	private boolean status=false;

	public Date getAnswerLength() {
		return answerLength;
	}

	public void setAnswerLength(Date answerLength) {
		this.answerLength = answerLength;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getReviewer() {
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public String getShowTime() {
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	public Integer getTicketid() {
		return ticketid;
	}

	public void setTicketid(Integer ticketid) {
		this.ticketid = ticketid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getAnswer() {
		return answer;
	}

	public void setAnswer(Integer answer) {
		this.answer = answer;
	}

	public Integer getNumbertype() {
		return numbertype;
	}

	public void setNumbertype(Integer numbertype) {
		this.numbertype = numbertype;
	}

	public Integer getChecknumber() {
		return checknumber;
	}

	public void setChecknumber(Integer checknumber) {
		this.checknumber = checknumber;
	}

	public Time getChecknumbertime() {
		return checknumbertime;
	}

	public void setChecknumbertime(Time checknumbertime) {
		this.checknumbertime = checknumbertime;
	}

	public Integer getDealproblem() {
		return dealproblem;
	}

	public void setDealproblem(Integer dealproblem) {
		this.dealproblem = dealproblem;
	}

	public Integer getIsbad() {
		return isbad;
	}

	public void setIsbad(Integer isbad) {
		this.isbad = isbad;
	}

	public Integer getServiceterms() {
		return serviceterms;
	}

	public void setServiceterms(Integer serviceterms) {
		this.serviceterms = serviceterms;
	}

	public Timestamp getChecktime() {
		return checktime;
	}

	public void setChecktime(Timestamp checktime) {
		this.checktime = checktime;
	}

	public CallTicket(long sequenceNumber, String callingNumber,
			String calledNumber, int operatorDesk, String operatorName,
			int originator, String callType, int callPriority,
			Timestamp startTime, Timestamp answerTime, Timestamp establishTime,
			Timestamp stopTime, String detail) {
		this.sequenceNumber = sequenceNumber;
		this.callingNumber = callingNumber;
		this.calledNumber = calledNumber;
		this.operatorDesk = operatorDesk;
		this.operatorName = operatorName;
		this.originator = originator;
		this.callType = callType;
		this.callPriority = callPriority;
		this.startTime = startTime;
		this.answerTime = answerTime;
		this.establishTime = establishTime;
		this.stopTime = stopTime;
		this.detail = detail;
	}

	public CallTicket() {
		super();
	}

	public CallTicket(Integer ticketid, Integer type, Integer answer,
			Integer numbertype, Integer checknumber, Time checknumbertime,
			Integer dealproblem, Integer isbad, Integer serviceterms,
			Timestamp checktime) {
		super();
		this.ticketid = ticketid;
		this.type = type;
		this.answer = answer;
		this.numbertype = numbertype;
		this.checknumber = checknumber;
		this.checknumbertime = checknumbertime;
		this.dealproblem = dealproblem;
		this.isbad = isbad;
		this.serviceterms = serviceterms;
		this.checktime = checktime;
	}

	public CallTicket(long sequenceNumber) {
		super();
		this.sequenceNumber = sequenceNumber;
	}

	public long getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getCallingNumber() {
		return callingNumber;
	}

	public void setCallingNumber(String callingNumber) {
		this.callingNumber = callingNumber;
	}

	public String getCalledNumber() {
		return calledNumber;
	}

	public void setCalledNumber(String calledNumber) {
		this.calledNumber = calledNumber;
	}

	public int getOperatorDesk() {
		return operatorDesk;
	}

	public void setOperatorDesk(int operatorDesk) {
		this.operatorDesk = operatorDesk;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public int getOriginator() {
		return originator;
	}

	public void setOriginator(int originator) {
		this.originator = originator;
	}

	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	public int getCallPriority() {
		return callPriority;
	}

	public void setCallPriority(int callPriority) {
		this.callPriority = callPriority;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getAnswerTime() {
		return answerTime;
	}

	public void setAnswerTime(Date answerTime) {
		this.answerTime = answerTime;
	}

	public Timestamp getEstablishTime() {
		return establishTime;
	}

	public void setEstablishTime(Timestamp establishTime) {
		this.establishTime = establishTime;
	}

	public Timestamp getStopTime() {
		return stopTime;
	}

	public void setStopTime(Timestamp stopTime) {
		this.stopTime = stopTime;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getSavefilename() {
		return savefilename;
	}

	public void setSavefilename(String savefilename) {
		this.savefilename = savefilename;
	}
    
}
