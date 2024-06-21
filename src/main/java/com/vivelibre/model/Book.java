package com.vivelibre.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book {
	
	private Long id;
	private String title;
	private String publicationTimestamp;
	private int pages;
	private String summary;
	private Author author;

}