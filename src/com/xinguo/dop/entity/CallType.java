package com.xinguo.dop.entity;

/**
 * CallType entity. @author MyEclipse Persistence Tools
 */

public class CallType implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String description;

	// Constructors

	/** default constructor */
	public CallType() {
	}

	/** minimal constructor */
	public CallType(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	/** full constructor */
	public CallType(Integer id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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