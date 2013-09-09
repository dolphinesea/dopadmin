package com.xinguo.dop.entity;

@SuppressWarnings("serial")
public class Operator implements java.io.Serializable {

	private long id;
	private String account = null;
	private String password = null;
	private String name = null;
	private int level;

	public Operator(long id, String account, String password, String name,
			int level) {
		super();
		this.id = id;
		this.account = account;
		this.password = password;
		this.name = name;
		this.level = level;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Operator() {
		super();
	}

	public Operator(long id, String name, String password, int level) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.level = level;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
