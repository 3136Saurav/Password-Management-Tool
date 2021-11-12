package com.epam.lbm.bookservice.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BookTest {

	Book book = new Book();

	@Test
	void testBookId() {
		book.setId(1L);
		assertEquals(1L, book.getId());
	}
	
	@Test
	void testBookAuthor() {
		book.setAuthor("Author");
		assertEquals("Author", book.getAuthor());
	}

	@Test
	void testBookPublisher() {
		book.setPublisher("Publisher");
		assertEquals("Publisher", book.getPublisher());
	}
	
	@Test
	void testBookName() {
		book.setName("Book Name");
		assertEquals("Book Name", book.getName());
	}

}
