package com.epam.lbm.libraryservice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import com.epam.lbm.libraryservice.LibraryServiceApplication;
import com.epam.lbm.libraryservice.clients.BookClient;
import com.epam.lbm.libraryservice.clients.UserClient;
import com.epam.lbm.libraryservice.db.bean.Book;
import com.epam.lbm.libraryservice.db.bean.User;
import com.epam.lbm.libraryservice.db.bean.entity.Library;
import com.epam.lbm.libraryservice.dto.LibraryDto;
import com.epam.lbm.libraryservice.exceptions.DuplicateEntryException;
import com.epam.lbm.libraryservice.exceptions.NoLibraryDetailsException;
import com.epam.lbm.libraryservice.service.LibraryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest(classes = { LibraryServiceApplication.class })
class LibraryRestControllerTest {

	@MockBean
	LibraryServiceImpl libraryServiceMock;

	@MockBean
	RestTemplate restTemplateMock;

	@Autowired
	MockMvc mvc;
	
	@Mock
	ModelMapper modelMapper;

	@MockBean
	UserClient userClientMock;

	@MockBean
	BookClient bookClientMock;
			
	@Test
	void testIssueBook() throws Exception {
		User user = new User();
		user.setUserName("Andy_01");
		
		Book book = new Book();
		book.setId(501L);
		
		LibraryDto libraryDto = new LibraryDto();
		libraryDto.setId(1L);
		libraryDto.setBookId(501L);
		libraryDto.setUserName("Andy_01");

		when(userClientMock.getUser("Andy_01")).thenReturn(user);
		when(bookClientMock.getBook(501L)).thenReturn(book);
	
		String libraryDtoJson = new ObjectMapper().writeValueAsString(libraryDto);
		mvc.perform(post("/library/users/Andy_01/books/501").contentType(MediaType.APPLICATION_JSON).content(libraryDtoJson))
		.andExpect(status().isCreated());
	}

	@Test
	void testIssueBookInvalidInput() throws Exception {
		User user = new User();

		LibraryDto libraryDto = new LibraryDto();
		libraryDto.setId(1L);
		libraryDto.setBookId(500L);
		libraryDto.setUserName("username1");
		String libraryDtoJson = new ObjectMapper().writeValueAsString(libraryDto);

		when(userClientMock.getUser("Andy_01")).thenReturn(user);
		when(bookClientMock.getBook(501L)).thenThrow(ConstraintViolationException.class);
	
		mvc.perform(post("/library/users/Andy_01/books/501").contentType(MediaType.APPLICATION_JSON).content(libraryDtoJson))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testReleaseBook() throws Exception {
		User user = new User();
		user.setUserName("Andy_01");
		
		Book book = new Book();
		book.setId(501L);
		
		LibraryDto libraryDto = new LibraryDto();
		libraryDto.setId(1L);
		libraryDto.setBookId(501L);
		libraryDto.setUserName("Andy_01");

		when(userClientMock.getUser("Andy_01")).thenReturn(user);
		when(bookClientMock.getBook(501L)).thenReturn(book);
	
		String libraryDtoJson = new ObjectMapper().writeValueAsString(libraryDto);
		mvc.perform(delete("/library/users/Andy_01/books/501").contentType(MediaType.APPLICATION_JSON).content(libraryDtoJson))
		.andExpect(status().isOk());
	}
	
	@Test
	void testIssueBookDuplicateEntry() throws Exception {
		LibraryDto libraryDto = new LibraryDto();
		libraryDto.setId(1L);
		libraryDto.setBookId(500L);
		libraryDto.setUserName("username1");
		String libraryDTOJson = new ObjectMapper().writeValueAsString(libraryDto);

		when(libraryServiceMock.saveLibraryDetail(Mockito.any())).thenThrow(DuplicateEntryException.class);

		mvc.perform(post("/library/users/username1/books/500").contentType(MediaType.APPLICATION_JSON).content(libraryDTOJson))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void testGetLibraryDetailsNoLibraryDetails() throws Exception {
		when(libraryServiceMock.findAllLibraryDetails()).thenThrow(NoLibraryDetailsException.class);
		mvc.perform(get("/library/")).andExpect(status().isBadRequest());
	}

	@Test
	void testGetAllLibraryDetails() throws Exception {	
		Library libraryRecord = new Library();
		libraryRecord.setId(1L);
		libraryRecord.setBookId(501L);
		libraryRecord.setUserName("Andy_01");

		List<Library> libraryRecords = new ArrayList<>();
		libraryRecords.add(libraryRecord);
		
		String libraryRecordsJson = new ObjectMapper().writeValueAsString(libraryRecords);
		mvc.perform(get("/library/").contentType(MediaType.APPLICATION_JSON).content(libraryRecordsJson))
		.andExpect(status().isOk());
	}
	
	@Test
	void testDeleteDetailLibraryDetailsDoesNotExist() throws Exception {
		when(libraryServiceMock.deleteLibraryDetailById(Mockito.anyLong())).thenThrow(NoLibraryDetailsException.class);
		
		mvc.perform(delete("/library/users/Andy_01/books/501")).andExpect(status().isBadRequest());
	}


}
