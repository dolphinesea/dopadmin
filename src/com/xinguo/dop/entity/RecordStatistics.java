package com.xinguo.dop.entity;

public class RecordStatistics {

	private String operatorName = null;
	
	private int answerTimeout = 0;
	
	private int largeTimeoutBase = 0;
	
	private int smallTimeoutBase = 0;
	
	private int timeoutBaseCount=0;
	
	private int largeTimeoutNoBase = 0;
	
	private int smallTimeoutNoBase = 0;
	
	private int timeoutNoBaseCount=0;
	
	private int dealGood = 0;
	
	private int dealBad = 0;
	
	private int badWords = 0;
	
	private int mistakes = 0;
	
	private int dealCount=0;
	
	private int ticketNum = 0;

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public int getAnswerTimeout() {
		return answerTimeout;
	}

	public void setAnswerTimeout(int answerTimeout) {
		this.answerTimeout = answerTimeout;
	}

	public int getLargeTimeoutBase() {
		return largeTimeoutBase;
	}

	public void setLargeTimeoutBase(int largeTimeoutBase) {
		this.largeTimeoutBase = largeTimeoutBase;
	}

	public int getSmallTimeoutBase() {
		return smallTimeoutBase;
	}

	public void setSmallTimeoutBase(int smallTimeoutBase) {
		this.smallTimeoutBase = smallTimeoutBase;
	}

	public int getLargeTimeoutNoBase() {
		return largeTimeoutNoBase;
	}

	public void setLargeTimeoutNoBase(int largeTimeoutNoBase) {
		this.largeTimeoutNoBase = largeTimeoutNoBase;
	}

	public int getSmallTimeoutNoBase() {
		return smallTimeoutNoBase;
	}

	public void setSmallTimeoutNoBase(int smallTimeoutNoBase) {
		this.smallTimeoutNoBase = smallTimeoutNoBase;
	}

	public int getDealGood() {
		return dealGood;
	}

	public void setDealGood(int dealGood) {
		this.dealGood = dealGood;
	}

	public int getDealBad() {
		return dealBad;
	}

	public void setDealBad(int dealBad) {
		this.dealBad = dealBad;
	}

	public int getBadWords() {
		return badWords;
	}

	public void setBadWords(int badWords) {
		this.badWords = badWords;
	}

	public int getMistakes() {
		return mistakes;
	}

	public void setMistakes(int mistakes) {
		this.mistakes = mistakes;
	}

	public int getTicketNum() {
		return ticketNum;
	}

	public void setTicketNum(int ticketNum) {
		this.ticketNum = ticketNum;
	}

	public int getTimeoutBaseCount() {
		return timeoutBaseCount;
	}

	public void setTimeoutBaseCount(int timeoutBaseCount) {
		this.timeoutBaseCount = timeoutBaseCount;
	}

	public int getTimeoutNoBaseCount() {
		return timeoutNoBaseCount;
	}

	public void setTimeoutNoBaseCount(int timeoutNoBaseCount) {
		this.timeoutNoBaseCount = timeoutNoBaseCount;
	}

	public int getDealCount() {
		return dealCount;
	}

	public void setDealCount(int dealCount) {
		this.dealCount = dealCount;
	}
}
