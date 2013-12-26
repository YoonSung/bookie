package com.bookie.www;

public class BookListData {
	
	private String imgUrl;
	private String title;
	private String reader;
	private Long lastReadPosixTime;
	private int startReadPage;
	private int endReadPage;
	private Long oneDayPosixTime = 1000*60*60*24L;
	
	public BookListData(String title, String reader, String imgUrl, int startReadPage, int endReadPage, Long lastReadPosixTime ) {
		this.imgUrl = imgUrl;
		this.title = title;
		this.reader = reader;
		this.lastReadPosixTime = lastReadPosixTime;
		this.startReadPage = startReadPage;
		this.endReadPage = endReadPage;
	}

	public String getReader() {
		return reader;
	}
	
	public String getImgUrl() {
		return imgUrl;
	}

	public String getTitle() {
		return title;
	}

	public Long getLastReadPosixTime() {
		return lastReadPosixTime;
	}

	public int getStartReadPage() {
		return startReadPage;
	}

	public int getEndReadPage() {
		return endReadPage;
	}
	
	public Long getReadBookLastDay() {
		return (System.currentTimeMillis()-lastReadPosixTime)/oneDayPosixTime;
	}
}