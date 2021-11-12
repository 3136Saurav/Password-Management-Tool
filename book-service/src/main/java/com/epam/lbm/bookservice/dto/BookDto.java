package com.epam.lbm.bookservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {
	
	private Long id;
	private String name;
	private String publisher;
	private String author;
}
