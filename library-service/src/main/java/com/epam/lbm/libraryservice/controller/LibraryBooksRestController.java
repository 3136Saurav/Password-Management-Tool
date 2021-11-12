package com.epam.lbm.libraryservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.lbm.libraryservice.clients.BookClient;
import com.epam.lbm.libraryservice.db.bean.Book;
import com.epam.lbm.libraryservice.service.LibraryService;

@RestController
@RequestMapping("/library")
public class LibraryBooksRestController {

	@Autowired
	LibraryService libraryService;

	@Autowired
	BookClient bookClient;	
	
	@GetMapping(value = "/books")
	public ResponseEntity<List<Book>> getAllBooks() {	
		return new ResponseEntity<>(bookClient.getAllBooks(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/books/{bookId}")
	public ResponseEntity<?> getBook(@PathVariable("bookId") Long bookId) {
		return new ResponseEntity<>(bookClient.getBook(bookId), HttpStatus.OK);
	}
	
	@PostMapping(value = "/books")
	public ResponseEntity<String> addBook(@RequestBody Book book) {
		return new ResponseEntity<>(bookClient.saveBook(book), HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/books/{bookId}")
	public ResponseEntity<String> updateBook(@PathVariable("bookId") Long bookId, @RequestBody Book book) {
		return new ResponseEntity<>(bookClient.updateBook(bookId, book), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/books/{bookId}")
	public ResponseEntity<String> deleteBook(@PathVariable("bookId") Long bookId, @RequestBody Book book) {
		return new ResponseEntity<>(bookClient.deleteBook(bookId), HttpStatus.OK);
	}
}
