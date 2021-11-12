package com.epam.lbm.libraryservice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.lbm.libraryservice.LibraryServiceApplication;
import com.epam.lbm.libraryservice.clients.BookClient;
import com.epam.lbm.libraryservice.db.bean.Book;
import com.epam.lbm.libraryservice.service.LibraryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest(classes = { LibraryServiceApplication.class })
class LibraryBooksRestControllerTest {

	@MockBean
	LibraryServiceImpl libraryService;

	@MockBean
	BookClient bookClientMock;
	
	@Autowired
	MockMvc mvc;
	
	@Mock
	ModelMapper modelMapper;

	@Test
	void testGetAllBooks() throws Exception {
		List<Book> books = new ArrayList<>();
		Book book = new Book();
		book.setId(501L);
		books.add(book);
		
		String booksListJson = new ObjectMapper().writeValueAsString(books);
		when(bookClientMock.getAllBooks()).thenReturn(books);
		
		mvc.perform(get("/library/books/").contentType(MediaType.APPLICATION_JSON)).andExpect(content().string(booksListJson)).andExpect(status().isOk());
	}

	@Test
	void testGetBook() throws Exception {
	    Book book = new Book();
	    book.setId(501L);
	    
	    String bookJson = new ObjectMapper().writeValueAsString(book);
		when(bookClientMock.getBook(book.getId())).thenReturn(book);

		mvc.perform(get("/library/books/501").contentType(MediaType.APPLICATION_JSON)).andExpect(content().string(bookJson)).andExpect(status().isOk());
	}
	
	@Test
	void testAddBook() throws Exception {
		Book book = new Book();
		book.setId(501L);
		book.setName("ABC");
		book.setAuthor("Ameya");
		book.setPublisher("M&M");
		
		String bookStrJson = new ObjectMapper().writeValueAsString(book);
		
		 when(bookClientMock.saveBook(book)).thenReturn("Book Added Successfully");
		 mvc.perform(post("/library/books").contentType(MediaType.APPLICATION_JSON).content(bookStrJson)).andExpect(status().isCreated());	
	}
	

	@Test
	void testUpdateBook() throws Exception {
		Book book = new Book();
		book.setId(500L);
		when(bookClientMock.updateBook(book.getId(), book)).thenReturn("Book Updated Successfully");
		String bookJson = new ObjectMapper().writeValueAsString(book);

		mvc.perform(put("/library/books/500").contentType(MediaType.APPLICATION_JSON).content(bookJson)).andExpect(status().isOk());
	}

	@Test
	void testDeleteBook() throws Exception {
		Book book = new Book();
		book.setId(500L);
		when(bookClientMock.updateBook(book.getId(), book)).thenReturn("Book Updated Successfully");
		String bookJson = new ObjectMapper().writeValueAsString(book);

		mvc.perform(delete("/library/books/500").contentType(MediaType.APPLICATION_JSON).content(bookJson)).andExpect(status().isOk());
	}
}