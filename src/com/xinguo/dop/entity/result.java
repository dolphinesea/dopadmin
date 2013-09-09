package com.xinguo.dop.entity;

public class result {
	private String ok;
	private String failure;
	private String cause;
	private long id;
	
	public void setok(String ok){
		this.ok = ok;
	}
	
	public String getok(){
		return this.ok;
	}
	
	public void setfailure(String failure){
		this.failure = failure;
	}
	
	public String getfailure(){
		return this.failure;
	}
	
	public void setcause(String cause){
		this.cause = cause;
	}
	
	public String getcause(){
		return this.cause;
	}
	
	public void setid(long id){
		this.id = id;
	}
	
	public long getid(){
		return this.id;
	}
}
