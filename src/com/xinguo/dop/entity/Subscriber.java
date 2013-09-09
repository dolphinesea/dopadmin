package com.xinguo.dop.entity;

/**
 * Subscriber entity. @author MyEclipse Persistence Tools
 */

public class Subscriber implements java.io.Serializable {

	// Fields

	private String number;
	private String name;
	private Integer call_priority;
	private Boolean dnd;
	private String description;
	private String call_priority_name;
	private Integer linetype;
	
	
	// Constructors

	/** default constructor */
	public Subscriber() {
	}

	/** minimal constructor */
	public Subscriber(String number, Integer call_priority) {
		this.number = number;
		this.call_priority = call_priority;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCall_priority() {
		return call_priority;
	}

	public void setCall_priority(Integer callPriority) {
		call_priority = callPriority;
	}

	public Boolean getDnd() {
		return dnd;
	}

	public void setDnd(Boolean dnd) {
		this.dnd = dnd;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public String getCall_priority_name() {
		return call_priority_name;
	}

	public void setCall_priority_name(String callPriorityName) {
		call_priority_name = callPriorityName;
	}

	public Integer getLinetype() {
		return linetype;
	}

	public void setLinetype(Integer linetype) {
		this.linetype = linetype;
	}

	
}