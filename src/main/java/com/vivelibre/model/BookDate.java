package com.vivelibre.model;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class BookDate {

	private Long id;
	private String title;
	private String publicationTimestamp;
	private int pages;
	private String summary;
	private Author author;
	private Date formatedDate;

	public BookDate(Book book) {

		this.id = book.getId();
		this.title = book.getTitle();
		this.publicationTimestamp = book.getPublicationTimestamp();
		this.pages = book.getPages();
		this.summary = book.getSummary();
		this.author = book.getAuthor();		
		this.formatedDate = this.publicationTimestamp != null ? parseDate(publicationTimestamp) : null;

	}

	private Date parseDate(String dateStr) {

		long timestamp = Long.parseLong(dateStr);
		Date date = new Date(timestamp * 1000L);

		return date;

	}

}