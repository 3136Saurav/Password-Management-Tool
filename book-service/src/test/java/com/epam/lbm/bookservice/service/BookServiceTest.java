package com.epam.lbm.bookservice.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.epam.lbm.bookservice.dto.BookDto;
import com.epam.lbm.bookservice.entity.Book;
import com.epam.lbm.bookservice.exceptions.BookDoesNotExistException;
import com.epam.lbm.bookservice.exceptions.DuplicateEntryException;
import com.epam.lbm.bookservice.exceptions.NoBooksAvailableException;
import com.epam.lbm.bookservice.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

	@InjectMocks
	BookServiceImpl bookService;
	
	@Mock
	BookRepository bookRepositoryMock;
	
	@Mock
	ModelMapper modelMapper;
	
	@Test
	void testSaveBookDuplicateEntry() {
		BookDto bookDto = new BookDto();
		bookDto.setId(501L);
		
		when(bookRepositoryMock.existsById(501L)).thenReturn(true);
		
		Assertions.assertThrows(DuplicateEntryException.class, () -> {
			bookService.saveBook(bookDto);
		});
	}
	
	@Test
	void testSaveBookValidEntry() throws DuplicateEntryException {
		BookDto bookDto = new BookDto();
		bookDto.setId(501L);
		
		Book book = new Book();
		when(bookRepositoryMock.existsById(501L)).thenReturn(false);
		when(modelMapper.map(bookDto, Book.class)).thenReturn(book);
		
		Assertions.assertEquals(book, bookService.saveBook(bookDto));
	}
	
	@Test
	void testFindAllBooks() throws NoBooksAvailableException {
		Book book = new Book();
		book.setId(501L);
		
		List<Book> books = new ArrayList<>();
		books.add(book);
		
		when(bookRepositoryMock.findAll()).thenReturn(books);
		
		Assertions.assertEquals(books, bookService.findAllBooks());
	}
	
	@Test
	void testFindAllBooks_withNoBooksAvailableException() throws NoBooksAvailableException {
		List<Book> books = new ArrayList<>();
		
		when(bookRepositoryMock.findAll()).thenReturn(books);
		
		Assertions.assertThrows(NoBooksAvailableException.class, () -> { bookService.findAllBooks(); });
	}
	
	@Test
	void testFindBook() throws BookDoesNotExistException {
		Book book = new Book();
		book.setId(500L);
		when(bookRepositoryMock.findById(500L)).thenReturn(Optional.of(book));
		
		Assertions.assertEquals(book, bookService.findBookById(500L));
	}
	
	@Test
	void testFindBooks_withBookDoesNotExistException() throws BookDoesNotExistException {
		when(bookRepositoryMock.findById(500L)).thenReturn(Optional.empty());
		
		Assertions.assertThrows(BookDoesNotExistException.class, () -> { bookService.findBookById(500L); });
	}
	
	@Test
	void testUpdateBookById() throws BookDoesNotExistException {
		Book book = new Book();
		book.setId(500L);
		when(bookRepositoryMock.findById(500L)).thenReturn(Optional.of(book));
		
		BookDto bookDto = new BookDto();
		bookDto.setId(500L);
		bookDto.setAuthor("Eminem");
		bookDto.setPublisher("M&M");
		
		bookService.updateBookById(book.getId(), bookDto);
		verify(bookRepositoryMock, times(1)).save(book);
	}
	
	@Test
	void testUpdateBookById_withBookDoesNotExistException() {
		BookDto bookDto = new BookDto();
		bookDto.setId(1L);
		when(bookRepositoryMock.findById(1L)).thenReturn(Optional.empty());
		Assertions.assertThrows(BookDoesNotExistException.class, () -> {
			bookService.updateBookById(1L, bookDto);
		});
	}

	@Test
	void testDeleteBookById() throws BookDoesNotExistException {
		Book book = new Book();
		book.setId(500L);
		when(bookRepositoryMock.findById(500L)).thenReturn(Optional.of(book));
		
		bookService.deleteBookById(book.getId());
		verify(bookRepositoryMock, times(1)).deleteById(book.getId());
	}
	
	@Test
	void testDeleteBookById_withBookDoesNotExistException() throws BookDoesNotExistException {
		Book book = new Book();
		book.setId(500L);
		when(bookRepositoryMock.findById(500L)).thenReturn(Optional.empty());
		
		Assertions.assertThrows(BookDoesNotExistException.class, () -> {
			bookService.deleteBookById(book.getId());
		});
	}
}
