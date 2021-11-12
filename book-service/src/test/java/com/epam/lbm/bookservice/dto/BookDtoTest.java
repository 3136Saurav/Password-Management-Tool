package com.epam.lbm.bookservice.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BookDtoTest {

	BookDto bookDto = new BookDto();

	@Test
	void testBookId() {
		bookDto.setId(1L);
		assertEquals(1L, bookDto.getId());
	}
	
	@Test
	void testBookAuthor() {
		bookDto.setAuthor("Author");
		assertEquals("Author", bookDto.getAuthor());
	}

	@Test
	void testBookPublisher() {
		bookDto.setPublisher("Publisher");
		assertEquals("Publisher", bookDto.getPublisher());
	}
	
	@Test
	void testBookName() {
		bookDto.setName("Book Name");
		assertEquals("Book Name", bookDto.getName());
	}

}
