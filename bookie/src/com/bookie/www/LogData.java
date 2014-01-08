package com.bookie.www;

public class LogData {

	private String dateFormat;
	private int startPage;
	private int endPage;
	private String content;
	
	public LogData ( String dateFormat, int startPage, int endPage, String content ) {
		this.dateFormat = dateFormat;
		this.startPage = startPage;
		this.endPage = endPage;
		this.content = content;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	
	public String getDateFormat() {
		return dateFormat;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
