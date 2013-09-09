package com.xinguo.dop.entity;

/**
 * CallPriority entity. @author MyEclipse Persistence Tools
 */

public class CallPriority implements java.io.Serializable {

	// Fields

	private Integer priority;
	private String name;
	private String description;

	// Constructors

	/** default constructor */
	public CallPriority() {
	}

	/** minimal constructor */
	public CallPriority(Integer priority, String name) {
		this.priority = priority;
		this.name = name;
	}

	/** full constructor */
	public CallPriority(Integer priority, String name, String description) {
		this.priority = priority;
		this.name = name;
		this.description = description;
	}

	// Property accessors

	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
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