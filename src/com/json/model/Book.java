package com.json.model;

import java.util.Arrays;

public class Book {
	private String[] authors;
	private String isbn;
	private String title;
	
	public Book(){}
	
	public Book(String title,String isbn, String[] authors) {
		super();
		this.authors = authors;
		this.isbn = isbn;
		this.title = title;
	}

	public String[] getAuthors() {
		return authors;
	}

	public void setAuthors(String[] authors) {
		this.authors = authors;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "Book [authors=" + Arrays.toString(authors) + ", isbn=" + isbn + ", title=" + title + "]";
	}

	
}
