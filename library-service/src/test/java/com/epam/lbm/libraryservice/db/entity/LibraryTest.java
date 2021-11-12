package com.epam.lbm.libraryservice.db.entity;

import org.junit.jupiter.api.Test;

import com.epam.lbm.libraryservice.db.bean.entity.Library;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

	Library library = new Library();
	
	@Test
	void testLibraryId() {
		library.setId(1L);
		assertEquals(1L, library.getId());
	}
	
	@Test
	void testLibraryUsername() {
		library.setUserName("username1");
		assertEquals("username1", library.getUserName());
	}
	
	@Test
	void testLibraryBookId() {
		library.setBookId(500L);
		assertEquals(500L, library.getBookId());
	}


}
