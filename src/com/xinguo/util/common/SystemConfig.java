package com.xinguo.util.common;

public class SystemConfig {

	public static String bbsServerIp = "";

	public static int bbsServerPort = 0;

	public static String recordFilePath = "0";

	public static String recordDir = "0";
	
	public static String recordBackupPath;
	
	public static String recordIp;
	
	public static String exporttmpfilepath;
	
	public static String printprojectpath;

	public static String callTicketsQueryLimit = "100";
	
	public static int maxItme = 500;
	
	public static  int subscriberMaxItme ;
	
	public static int subscriberMode;
	
	public static int subscriberPageBreak;
	
	public static int isSubscriberPageBreak;
	
	public static String subscriberexcelprintcmd;
	
	public static String subscriberexcelexportdir;
	
	public static String callticketinfowordprintcmd;
	
	public static int recorddownload;
	
	public static String reportdetailexcelexportdir;
	
	public static String reportstatisticsexcelexportdir;
	
	public String getBbsServerIp() {
		return bbsServerIp;
	}

	public void setBbsServerIp(String bbsServerIp) {
		SystemConfig.bbsServerIp = bbsServerIp;
	}

	public String getRecordDir() {
		return recordDir;
	}

	public void setRecordDir(String recordDir) {
		SystemConfig.recordDir = recordDir;
	}

	public int getBbsServerPort() {
		return bbsServerPort;
	}

	public void setBbsServerPort(int bbsServerPort) {
		SystemConfig.bbsServerPort = bbsServerPort;
	}

	public String getRecordFilePath() {
		return recordFilePath;
	}

	public void setRecordFilePath(String recordFilePath) {
		SystemConfig.recordFilePath = recordFilePath;
	}
	
	public String getRecordBackupPath() {
		return recordBackupPath;
	}
	
	public void setRecordBackupPath(String recordBackupPath) {
		SystemConfig.recordBackupPath = recordBackupPath;
	}
	
	public String getrecordIp(){
		return recordIp;
	}
	
	public void setrecordIp(String recordIp){
		SystemConfig.recordIp = recordIp;
	}
	
	public String getexporttmpfilepath() {
		return exporttmpfilepath;
	}
	
	public void setexporttmpfilepath(String exporttmpfilepath) {
		SystemConfig.exporttmpfilepath = exporttmpfilepath;
	}
	
	public String getprintprojectpath(){
		return printprojectpath;
	}
	
	public void setprintprojectpath(String printprojectpath){
		SystemConfig.printprojectpath = printprojectpath;
	}

	public String getCallTicketsQueryLimit() {
		return callTicketsQueryLimit;
	}

	public void setCallTicketsQueryLimit(String callTicketsQueryLimit) {
		SystemConfig.callTicketsQueryLimit = callTicketsQueryLimit;
	}

	public int getMaxItme() {
    	return maxItme;
    }

	public  void setMaxItme(int maxItme) {
    	SystemConfig.maxItme = maxItme;
    }

	public int getSubscriberMaxItme() {
    	
		return subscriberMaxItme;
    }

	public void setSubscriberMaxItme(int subscriberMaxItme) {
		SystemConfig.subscriberMaxItme = subscriberMaxItme;
    }

	public int getSubscriberMode() {
    	return subscriberMode;
    }

	public void setSubscriberMode(int subscriberMode) {
		SystemConfig.subscriberMode = subscriberMode;
    }

	public int getSubscriberPageBreak(){
		return subscriberPageBreak;
	}
	
	public void setSubscriberPageBreak( int subscriberPageBreak){
		SystemConfig.subscriberPageBreak = subscriberPageBreak;
	}

	public   int getIsSubscriberPageBreak() {
    	return isSubscriberPageBreak;
    }

	public  void setIsSubscriberPageBreak(int isSubscriberPageBreak) {
    	SystemConfig.isSubscriberPageBreak = isSubscriberPageBreak;
    }
	
	public String getsubscriberexcelprintcmd(){
		return subscriberexcelprintcmd;
	}
	
	public void setsubscriberexcelprintcmd(String subscriberexcelprintcmd){
		SystemConfig.subscriberexcelprintcmd = subscriberexcelprintcmd;
	}
	
	public String getsubscriberexcelexportdir(){
		return subscriberexcelexportdir;
	}
	
	public void setsubscriberexcelexportdir(String subscriberexcelexportdir){
		SystemConfig.subscriberexcelexportdir = subscriberexcelexportdir;
	}
	
	public String getcallticketinfowordprintcmd(){
		return callticketinfowordprintcmd;
	}
	
	public void setcallticketinfowordprintcmd(String callticketinfowordprintcmd){
		SystemConfig.callticketinfowordprintcmd = callticketinfowordprintcmd;
	}

	public int getRecorddownload() {
    	return recorddownload;
    }

	public void setRecorddownload(int recorddownload) {
    	SystemConfig.recorddownload = recorddownload;
    }
	
	public String getreportdetailexcelexportdir(){
		return reportdetailexcelexportdir;
	}
	
	public void setreportdetailexcelexportdir(String reportdetailexcelexportdir){
		SystemConfig.reportdetailexcelexportdir = reportdetailexcelexportdir;
	}
	
	public String getreportstatisticsexcelexportdir(){
		return reportdetailexcelexportdir;
	}
	
	public void setreportstatisticsexcelexportdir(String reportstatisticsexcelexportdir){
		SystemConfig.reportstatisticsexcelexportdir = reportstatisticsexcelexportdir;
	}
	 
}
