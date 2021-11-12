package com.epam.lbm.bookservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.lbm.bookservice.dto.BookDto;
import com.epam.lbm.bookservice.entity.Book;
import com.epam.lbm.bookservice.exceptions.BookDoesNotExistException;
import com.epam.lbm.bookservice.exceptions.DuplicateEntryException;
import com.epam.lbm.bookservice.exceptions.NoBooksAvailableException;
import com.epam.lbm.bookservice.service.BookService;

@RestController
public class BookRestController {

	@Autowired
	BookService bookService;
	
	@GetMapping("/books")
	public ResponseEntity<List<Book>> getAllBooks() throws NoBooksAvailableException {
		return new ResponseEntity<>(bookService.findAllBooks(), HttpStatus.OK);
	}
	
	@GetMapping("/books/{id}")
	public ResponseEntity<Book> getBook(@PathVariable Long id) throws BookDoesNotExistException {
		return new ResponseEntity<>(bookService.findBookById(id), HttpStatus.OK);
	}
	
	@PostMapping("/books")
	public ResponseEntity<String> saveBook(@RequestBody @Valid BookDto bookDto) throws DuplicateEntryException {
		bookService.saveBook(bookDto);
		return new ResponseEntity<>("Book Added Successfully", HttpStatus.CREATED);
	}
	
	@PutMapping("/books/{id}")
	public ResponseEntity<String> updateBook(@PathVariable Long id, @RequestBody @Valid BookDto bookDto) throws BookDoesNotExistException {
		return new ResponseEntity<>(bookService.updateBookById(id, bookDto), HttpStatus.OK);
	}
	
	@DeleteMapping("/books/{id}")
	public ResponseEntity<String> deleteBook(@PathVariable Long id) throws BookDoesNotExistException {
		return new ResponseEntity<>(bookService.deleteBookById(id), HttpStatus.OK);
	}
}
