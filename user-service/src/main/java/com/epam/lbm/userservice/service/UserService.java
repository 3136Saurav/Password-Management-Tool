package com.epam.lbm.userservice.service;

import java.util.List;

import com.epam.lbm.userservice.dto.UserDto;
import com.epam.lbm.userservice.entity.User;
import com.epam.lbm.userservice.exceptions.UserDoesNotExistException;
import com.epam.lbm.userservice.exceptions.DuplicateEntryException;
import com.epam.lbm.userservice.exceptions.NoUsersAvailableException;

public interface UserService {

	User saveUser(UserDto userDto) throws DuplicateEntryException;

	List<User> findAllUsers() throws NoUsersAvailableException;

	User findUserById(String userName) throws UserDoesNotExistException;

	String updateUserById(String userName, UserDto userDto) throws UserDoesNotExistException;

	String deleteUserById(String userName) throws UserDoesNotExistException;

}