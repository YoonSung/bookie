package com.bookie.www;

public class BookData {
	
	private String imgUrl;
	private String title;
	private String author;
	private int totalPages;
	
	public BookData(String imgUrl, String title, String author, int totalPages) {
		this.imgUrl = imgUrl;
		this.title = title;
		this.author = author;
		this.totalPages = totalPages;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
}