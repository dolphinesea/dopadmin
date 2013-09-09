package com.xinguo.dop.entity;

import java.sql.Timestamp;

/**
 * Bbs entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
public class Bbs implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer type;
	private String writer;
	private Integer createdDate;
	
	private String cDate;
	
	
	private String body;
	private Integer deletedFlag;

	// Constructors

	/** default constructor */
	public Bbs() {
	}

	public String getcDate() {
		return cDate;
	}

	public void setcDate(String cDate) {
		this.cDate = cDate;
	}

	/** full constructor */
	public Bbs(Integer id, Integer type, String writer, Integer createdDate,
			String body, Integer deletedFlag) {
		this.id = id;
		this.type = type;
		this.writer = writer;
		this.createdDate = createdDate;
		this.body = body;
		this.deletedFlag = deletedFlag;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getWriter() {
		return this.writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public Integer getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Integer createdDate) {
		this.createdDate = createdDate;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Integer getDeletedFlag() {
		return this.deletedFlag;
	}

	public void setDeletedFlag(Integer deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

}