package com.xinguo.dop.entity;

/**
 * CallTypeIdentification entity. @author MyEclipse Persistence Tools
 */

public class CallTypeIdentification extends CallType implements java.io.Serializable {

	// Fields

	private String accessCode;
	private Integer callingClass;
	private Integer callType;

	private String calltypename;
	private String ctdesc;

	private String accesscodename;
	private String acdesc;

	// Constructors

	/** default constructor */
	public CallTypeIdentification() {
	}

	public String getCalltypename() {
		return calltypename;
	}

	public void setCalltypename(String calltypename) {
		this.calltypename = calltypename;
	}

	public String getCtdesc() {
		return ctdesc;
	}

	public void setCtdesc(String ctdesc) {
		this.ctdesc = ctdesc;
	}

	public String getAccesscodename() {
		return accesscodename;
	}

	public void setAccesscodename(String accesscodename) {
		this.accesscodename = accesscodename;
	}

	public String getAcdesc() {
		return acdesc;
	}

	public void setAcdesc(String acdesc) {
		this.acdesc = acdesc;
	}

	public String getAccessCode() {
		return accessCode;
	}

	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}

	public Integer getCallingClass() {
		return callingClass;
	}

	public void setCallingClass(Integer callingClass) {
		this.callingClass = callingClass;
	}

	public Integer getCallType() {
		return callType;
	}

	public void setCallType(Integer callType) {
		this.callType = callType;
	}

}