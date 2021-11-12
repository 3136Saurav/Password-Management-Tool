package com.epam.lbm.bookservice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.lbm.bookservice.BookServiceApplication;
import com.epam.lbm.bookservice.dto.BookDto;
import com.epam.lbm.bookservice.entity.Book;
import com.epam.lbm.bookservice.exceptions.BookDoesNotExistException;
import com.epam.lbm.bookservice.exceptions.DuplicateEntryException;
import com.epam.lbm.bookservice.exceptions.NoBooksAvailableException;
import com.epam.lbm.bookservice.service.BookServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest(classes = { BookServiceApplication.class })
class BookRestControllerTest {
	@Autowired
	MockMvc mvc;

	@MockBean
	BookServiceImpl bookServiceMock;
	
	@Test
	void testSaveBook() throws Exception {
		Book book = new Book();
		book.setId(501L);
		
		BookDto bookDto = new BookDto();
		bookDto.setId(501L);
		String bookDtoJson = new ObjectMapper().writeValueAsString(bookDto);

		when(bookServiceMock.saveBook(bookDto)).thenReturn(book);
		mvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON).content(bookDtoJson))
				.andExpect(status().isCreated());
	}
	
	@Test
	void testGetBooks() throws Exception {
		List<Book> books = new ArrayList<>();

		String booksJson = new ObjectMapper().writeValueAsString(books);
		
		when(bookServiceMock.findAllBooks()).thenReturn(books);
		mvc.perform(get("/books").contentType(MediaType.APPLICATION_JSON).content(booksJson))
				.andExpect(status().isOk());
	}
	
	@Test
	void testGetBook() throws Exception {
		Book book = new Book();
		book.setId(501L);
		
		String booksJson = new ObjectMapper().writeValueAsString(book);
		
		when(bookServiceMock.findBookById(501L)).thenReturn(book);
		mvc.perform(get("/books/501").contentType(MediaType.APPLICATION_JSON).content(booksJson))
				.andExpect(status().isOk());
	}
	
	@Test
	void testUpdateBook() throws Exception {
		BookDto bookDto = new BookDto();
		bookDto.setId(501L);
		bookDto.setAuthor("Neil");
		bookDto.setName("Mark");
		bookDto.setPublisher("Canary");
		
		String booksJson = new ObjectMapper().writeValueAsString(bookDto);
		
		mvc.perform(put("/books/501").contentType(MediaType.APPLICATION_JSON).content(booksJson))
				.andExpect(status().isOk());	
	}

	@Test
	void testDeleteBook() throws Exception {
		BookDto bookDto = new BookDto();
		bookDto.setId(501L);
	
		String booksJson = new ObjectMapper().writeValueAsString(bookDto);
		
		mvc.perform(delete("/books/501").contentType(MediaType.APPLICATION_JSON).content(booksJson))
				.andExpect(status().isOk());	
	}

	@Test
	void testSaveBook_withDuplicateEntryException() throws Exception {
		Book book = new Book();
		book.setId(1L);
		
		BookDto bookDto = new BookDto();
		bookDto.setId(1L);
		String bookDtoJson = new ObjectMapper().writeValueAsString(bookDto);

		when(bookServiceMock.saveBook(Mockito.any())).thenThrow(DuplicateEntryException.class);
		mvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON).content(bookDtoJson))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testGetBooks_withNoBooksAvailableException() throws Exception {
		when(bookServiceMock.findAllBooks()).thenThrow(NoBooksAvailableException.class);
		mvc.perform(get("/books")).andExpect(status().isNotFound());
	}

	@Test
	void testUpdateBookById_withBookDoesNotExistException() throws Exception {
		BookDto bookDto = new BookDto();
		bookDto.setId(1L);

		String bookDtoJson = new ObjectMapper().writeValueAsString(bookDto);
		when(bookServiceMock.updateBookById(bookDto.getId(), bookDto)).thenThrow(BookDoesNotExistException.class);
		mvc.perform(put("/books/1L").contentType(MediaType.APPLICATION_JSON)
				.content(bookDtoJson)).andExpect(status().isBadRequest());
	}
}


