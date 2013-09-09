package com.xinguo.dop.entity;

/**
 * AccessCode entity. @author MyEclipse Persistence Tools
 */

public class AccessCode implements java.io.Serializable {

	// Fields

	private String code;
	private String name;
	private String description;

	// Constructors

	/** default constructor */
	public AccessCode() {
	}

	/** minimal constructor */
	public AccessCode(String code) {
		this.code = code;
	}

	/** full constructor */
	public AccessCode(String code, String name, String description) {
		this.code = code;
		this.name = name;
		this.description = description;
	}

	// Property accessors

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}