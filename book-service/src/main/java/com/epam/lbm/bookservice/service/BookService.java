package com.epam.lbm.bookservice.service;

import java.util.List;

import com.epam.lbm.bookservice.dto.BookDto;
import com.epam.lbm.bookservice.entity.Book;
import com.epam.lbm.bookservice.exceptions.BookDoesNotExistException;
import com.epam.lbm.bookservice.exceptions.DuplicateEntryException;
import com.epam.lbm.bookservice.exceptions.NoBooksAvailableException;

public interface BookService {

	Book saveBook(BookDto bookDto) throws DuplicateEntryException;

	List<Book> findAllBooks() throws NoBooksAvailableException;

	Book findBookById(Long id) throws BookDoesNotExistException;

	String updateBookById(Long id, BookDto bookDto) throws BookDoesNotExistException;

	String deleteBookById(Long id) throws BookDoesNotExistException;

}