package com.epam.lbm.bookservice.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.lbm.bookservice.dto.BookDto;
import com.epam.lbm.bookservice.entity.Book;
import com.epam.lbm.bookservice.exceptions.BookDoesNotExistException;
import com.epam.lbm.bookservice.exceptions.DuplicateEntryException;
import com.epam.lbm.bookservice.exceptions.NoBooksAvailableException;
import com.epam.lbm.bookservice.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {
	@Autowired
	BookRepository bookRepository;

	ModelMapper modelMapper = new ModelMapper();
	
	@Override
	public Book saveBook(BookDto bookDto) throws DuplicateEntryException {
		if (bookRepository.existsById(bookDto.getId())) {
			throw new DuplicateEntryException("Book already exists!");
		}
		
		Book book = modelMapper.map(bookDto, Book.class);
		bookRepository.save(book);
		
		return book;
	}
	
	@Override
	public List<Book> findAllBooks() throws NoBooksAvailableException {
		List<Book> books = bookRepository.findAll();
		
		if (books.isEmpty()) {
			throw new NoBooksAvailableException("No Books available!");
		}
		
		return books;
	}
	
	@Override
	public Book findBookById(Long id) throws BookDoesNotExistException {
		Optional<Book> bookOptional = bookRepository.findById(id);
		
		if (!bookOptional.isPresent()) {
			throw new BookDoesNotExistException("Book does not exist!");
		}
		
		return bookOptional.get();
	}
	
	@Override
	public String updateBookById(Long id, BookDto bookDto) throws BookDoesNotExistException {
		Optional<Book> bookOptional = bookRepository.findById(id);
		
		if (!bookOptional.isPresent()) {
			throw new BookDoesNotExistException("Book does not exist!");
		}
		
		Book book = bookOptional.get();
		book.setId(id);
		if (bookDto.getName() != null) {
			book.setName(bookDto.getName());
		}
		
		if (bookDto.getAuthor() != null) {
			book.setAuthor(bookDto.getAuthor());
		}
		
		if (bookDto.getPublisher() != null) {
			book.setPublisher(bookDto.getPublisher());
		}
		
		bookRepository.save(book);
		return "Book Updated Successfully";
	}
	
	@Override
	public String deleteBookById(Long id) throws BookDoesNotExistException {
		Optional<Book> bookOptional = bookRepository.findById(id);
		
		if (!bookOptional.isPresent()) {
			throw new BookDoesNotExistException("Book does not exist!");
		}
		
		bookRepository.deleteById(id);
		return "Book Deleted Successfully";
	}
}
