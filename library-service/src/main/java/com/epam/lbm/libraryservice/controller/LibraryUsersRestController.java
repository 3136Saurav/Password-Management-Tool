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

import com.epam.lbm.libraryservice.clients.UserClient;
import com.epam.lbm.libraryservice.db.bean.User;
import com.epam.lbm.libraryservice.service.LibraryService;

@RestController
@RequestMapping("/library")
public class LibraryUsersRestController {

	@Autowired
	LibraryService libraryService;
	
	@Autowired
	UserClient userClient;
	
	@GetMapping(value = "/users")
	public ResponseEntity<List<User>> getAllUsers() {
		return new ResponseEntity<>(userClient.getAllUsers(), HttpStatus.OK); 
	}
	
	@GetMapping(value = "/users/{userName}")
	public ResponseEntity<User> getUser(@PathVariable("userName") String userName) {
		return new ResponseEntity<>(userClient.getUser(userName), HttpStatus.OK); 
	}
	
	@PostMapping(value = "/users")
	public ResponseEntity<String> addUser(@RequestBody User user) {
		return new ResponseEntity<>(userClient.saveUser(user), HttpStatus.CREATED); 
	}
	
	@PutMapping(value = "/users/{userName}")
	public ResponseEntity<String> updateUser(@PathVariable("userName") String userName, @RequestBody User user) {
		return new ResponseEntity<>(userClient.updateUser(userName, user), HttpStatus.OK); 
	}
	
	@DeleteMapping(value = "/users/{userName}")
	public ResponseEntity<String> deleteUser(@PathVariable("userName") String userName, @RequestBody User user) {
		return new ResponseEntity<>(userClient.deleteUser(userName), HttpStatus.OK);
	}
}
