package com.epam.lbm.userservice.controller;

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

import com.epam.lbm.userservice.dto.UserDto;
import com.epam.lbm.userservice.entity.User;
import com.epam.lbm.userservice.exceptions.UserDoesNotExistException;
import com.epam.lbm.userservice.exceptions.DuplicateEntryException;
import com.epam.lbm.userservice.exceptions.NoUsersAvailableException;
import com.epam.lbm.userservice.service.UserService;

@RestController
public class UserRestController {

	@Autowired
	UserService userService;
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers() throws NoUsersAvailableException {
		return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
	}
	
	@GetMapping("/users/{user-name}")
	public ResponseEntity<User> getUser(@PathVariable("user-name") String userName) throws UserDoesNotExistException {
		return new ResponseEntity<>(userService.findUserById(userName), HttpStatus.OK);
	}
	
	@PostMapping("/users")
	public ResponseEntity<String> saveUser(@RequestBody @Valid UserDto userDto) throws DuplicateEntryException {
		userService.saveUser(userDto);
		return new ResponseEntity<>("User Added Successfully", HttpStatus.CREATED);
	}
	
	@PutMapping("/users/{user-name}")
	public ResponseEntity<String> updateUser(@PathVariable("user-name") String userName, @RequestBody @Valid UserDto userDto) throws UserDoesNotExistException {
		return new ResponseEntity<>(userService.updateUserById(userName, userDto), HttpStatus.OK);
	}
	
	@DeleteMapping("/users/{user-name}")
	public ResponseEntity<String> deleteUser(@PathVariable("user-name") String userName) throws UserDoesNotExistException {
		return new ResponseEntity<>(userService.deleteUserById(userName), HttpStatus.OK);
	}
}
