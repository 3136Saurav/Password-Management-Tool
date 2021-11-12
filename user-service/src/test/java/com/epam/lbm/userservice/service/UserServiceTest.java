package com.epam.lbm.userservice.service;

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

import com.epam.lbm.userservice.dto.UserDto;
import com.epam.lbm.userservice.entity.User;
import com.epam.lbm.userservice.exceptions.DuplicateEntryException;
import com.epam.lbm.userservice.exceptions.NoUsersAvailableException;
import com.epam.lbm.userservice.exceptions.UserDoesNotExistException;
import com.epam.lbm.userservice.repository.UserRepository;


@ExtendWith(MockitoExtension.class)

public class UserServiceTest {
	@InjectMocks
	UserServiceImpl userService;
	
	@Mock
	UserRepository userRepositoryMock;
	
	@Mock
	ModelMapper modelMapper;
	
	@Test
	void testSaveUserDuplicateEntry() {
		UserDto userDto = new UserDto();
		userDto.setUserName("Andy_01");
		
		when(userRepositoryMock.existsById("Andy_01")).thenReturn(true);
		
		Assertions.assertThrows(DuplicateEntryException.class, () -> {
			userService.saveUser(userDto);
		});
	}
	
	@Test
	void testSaveUserValidEntry() throws DuplicateEntryException {
		UserDto userDto = new UserDto();
		userDto.setUserName("Andy_01");
		
		User user = new User();
		when(userRepositoryMock.existsById("Andy_01")).thenReturn(false);
		when(modelMapper.map(userDto, User.class)).thenReturn(user);
		
		Assertions.assertEquals(user, userService.saveUser(userDto));
	}
	
	@Test
	void testFindAllUsers() throws NoUsersAvailableException {
		User user = new User();
		user.setUserName("Andy_01");
		
		List<User> users = new ArrayList<>();
		users.add(user);
		
		when(userRepositoryMock.findAll()).thenReturn(users);
		
		Assertions.assertEquals(users, userService.findAllUsers());
	}
	
	@Test
	void testFindAllUsers_withNoUsersAvailableException() throws NoUsersAvailableException {
		List<User> users = new ArrayList<>();
		
		when(userRepositoryMock.findAll()).thenReturn(users);
		
		Assertions.assertThrows(NoUsersAvailableException.class, () -> { userService.findAllUsers(); });
	}
	
	@Test
	void testFindUser() throws UserDoesNotExistException {
		User user = new User();
		user.setUserName("Andy_01");
		when(userRepositoryMock.findById("Andy_01")).thenReturn(Optional.of(user));
		
		Assertions.assertEquals(user, userService.findUserById("Andy_01"));
	}
	
	@Test
	void testFindBooks_withUserDoesNotExistException() throws UserDoesNotExistException {
		when(userRepositoryMock.findById("Andy_01")).thenReturn(Optional.empty());
		
		Assertions.assertThrows(UserDoesNotExistException.class, () -> { userService.findUserById("Andy_01"); });
	}
	
	@Test
	void testUpdateUserById() throws UserDoesNotExistException {
		User user = new User();
		user.setUserName("Andy_01");
		when(userRepositoryMock.findById("Andy_01")).thenReturn(Optional.of(user));
		
		UserDto userDto = new UserDto();
		userDto.setUserName("Andy_01");
		
		userService.updateUserById(user.getUserName(), userDto);
		verify(userRepositoryMock, times(1)).save(user);
	}
	
	@Test
	void testDeleteBookById() throws UserDoesNotExistException {
		User user = new User();
		user.setUserName("Andy_01");
		when(userRepositoryMock.findById("Andy_01")).thenReturn(Optional.of(user));
		
		userService.deleteUserById(user.getUserName());
		verify(userRepositoryMock, times(1)).deleteById(user.getUserName());
	}
}
