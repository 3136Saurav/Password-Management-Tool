package com.epam.lbm.libraryservice.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.epam.lbm.libraryservice.db.bean.Book;

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
