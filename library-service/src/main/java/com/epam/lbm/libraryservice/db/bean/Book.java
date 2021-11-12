package com.epam.lbm.libraryservice.db.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book {
	private Long id;
	
	private String name;
	
	private String publisher;
	
	private String author;
}
