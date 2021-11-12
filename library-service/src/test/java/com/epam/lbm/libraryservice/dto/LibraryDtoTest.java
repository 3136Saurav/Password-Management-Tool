package com.epam.lbm.libraryservice.dto;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LibraryDtoTest {
	LibraryDto libraryDTO = new LibraryDto();
	
	@Test
	void testLibraryId() {
		libraryDTO.setId(1L);
		assertEquals(1, libraryDTO.getId());
	}
	
	@Test
	void testLibraryUsername() {
		libraryDTO.setUserName("username1");
		assertEquals("username1", libraryDTO.getUserName());
	}
	
	@Test
	void testLibraryBookId() {
		libraryDTO.setBookId(500L);
		assertEquals(500L, libraryDTO.getBookId());
	}
}
