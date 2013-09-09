package com.xinguo.dop.entity;

public class BackupRecord {
	private String BackupDate;
	
	private String BackupUrl;
	
	public String getBackupDate(){
		return this.BackupDate;
	}
	
	public void setBackupDate(String BackupDate){
		this.BackupDate = BackupDate;
	}
	
	public String getBackupUrl(){
		return this.BackupUrl;
	}
	
	public void setBackupUrl(String BackupUrl){
		this.BackupUrl = BackupUrl;
	}
	
	public BackupRecord(){
		
	}
	
	public BackupRecord(String BackupDate, String BackupUrl){
		this.setBackupDate(BackupDate);
		this.setBackupUrl(BackupUrl);
	}
}
