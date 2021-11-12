package com.epam.lbm.libraryservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.lbm.libraryservice.clients.BookClient;
import com.epam.lbm.libraryservice.clients.UserClient;
import com.epam.lbm.libraryservice.db.bean.entity.Library;
import com.epam.lbm.libraryservice.dto.LibraryDto;
import com.epam.lbm.libraryservice.exceptions.DuplicateEntryException;
import com.epam.lbm.libraryservice.exceptions.NoLibraryDetailsException;
import com.epam.lbm.libraryservice.service.LibraryService;

@RestController
@RequestMapping("/library")
public class LibraryRestController {

	@Autowired
	LibraryService libraryService;
		
	@Autowired
	UserClient userClient;
	
	@Autowired
	BookClient bookClient;
	
	@PostMapping("/users/{userName}/books/{bookId}")
	public ResponseEntity<String> issueBook(@PathVariable("userName") String userName, @PathVariable("bookId") Long bookId, @RequestBody LibraryDto libraryDto) throws DuplicateEntryException {

		userClient.getUser(userName);
		bookClient.getBook(bookId);
		
		libraryDto.setId(libraryDto.getId());
		libraryDto.setBookId(bookId);
		libraryDto.setUserName(userName);
		libraryService.saveLibraryDetail(libraryDto);
		
		return new ResponseEntity<>("Book Issued Successfully!", HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/users/{userName}/books/{bookId}")
	public ResponseEntity<String> releaseBook(@PathVariable("userName") String userName, @PathVariable("bookId") Long bookId, @RequestBody LibraryDto libraryDto) throws NoLibraryDetailsException {

		userClient.getUser(userName);
		bookClient.getBook(bookId);

		libraryService.deleteLibraryDetailById(libraryDto.getId());
		
		return new ResponseEntity<>("Book Returned Successfully!", HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<Library>> getAllLibraryDetails() throws NoLibraryDetailsException {
		return new ResponseEntity<>(libraryService.findAllLibraryDetails(), HttpStatus.OK);
	}
}

