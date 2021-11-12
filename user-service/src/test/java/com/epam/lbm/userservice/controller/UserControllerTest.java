package com.epam.lbm.userservice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.lbm.userservice.UserServiceApplication;
import com.epam.lbm.userservice.dto.UserDto;
import com.epam.lbm.userservice.entity.User;
import com.epam.lbm.userservice.exceptions.NoUsersAvailableException;
import com.epam.lbm.userservice.exceptions.UserDoesNotExistException;
import com.epam.lbm.userservice.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest(classes = { UserServiceApplication.class })
public class UserControllerTest {
	@Autowired
	MockMvc mvc;

	@MockBean
	UserServiceImpl userServiceMock;
	
	@Test
	void testSaveUser() throws Exception {
		User user = new User();
		user.setUserName("Andy_01");
		
		UserDto userDto = new UserDto();
		userDto.setUserName("Andy_01");
		String userDtoJson = new ObjectMapper().writeValueAsString(userDto);

		when(userServiceMock.saveUser(userDto)).thenReturn(user);
		mvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(userDtoJson))
				.andExpect(status().isCreated());
	}
	
	@Test
	void testGetUsers() throws Exception {
		List<User> users = new ArrayList<>();

		String usersJson = new ObjectMapper().writeValueAsString(users);
		
		when(userServiceMock.findAllUsers()).thenReturn(users);
		mvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON).content(usersJson))
				.andExpect(status().isOk());
	}
	
	@Test
	void testGetUsers_withNoUsersAvailable() throws Exception {
		List<User> userList = new ArrayList<>();
		String userListJson = new ObjectMapper().writeValueAsString(userList);

		when(userServiceMock.findAllUsers()).thenThrow(NoUsersAvailableException.class);
		mvc.perform(get("/users")).andExpect(status().isNotFound());
	}
	

	
	@Test
	void testGetUser() throws Exception {
		User user = new User();
		user.setUserName("Andy_01");
		
		String usersJson = new ObjectMapper().writeValueAsString(user);
		
		when(userServiceMock.findUserById("Andy_01")).thenReturn(user);
		mvc.perform(get("/users/Andy_01").contentType(MediaType.APPLICATION_JSON).content(usersJson))
				.andExpect(status().isOk());
	}
	
	@Test
	void testUpdateUser() throws Exception {
		UserDto userDto = new UserDto();
		userDto.setUserName("Neil_07");
		userDto.setName("Mark");
		userDto.setEmail("mark@gmail.com");
		
		String usersJson = new ObjectMapper().writeValueAsString(userDto);
		
		mvc.perform(put("/users/Andy_01").contentType(MediaType.APPLICATION_JSON).content(usersJson))
				.andExpect(status().isOk());	
	}

	@Test
	void testDeleteBook() throws Exception {
		UserDto userDto = new UserDto();
		userDto.setUserName("Andy_01");
	
		String booksJson = new ObjectMapper().writeValueAsString(userDto);
		
		mvc.perform(delete("/users/Andy_01").contentType(MediaType.APPLICATION_JSON).content(booksJson))
				.andExpect(status().isOk());	
	}

	@Test
	void testUpdateUserById_withUserDoesNotExistException() throws Exception {

		UserDto userDto = new UserDto();
		userDto.setUserName("username1");

		String userDtoJson = new ObjectMapper().writeValueAsString(userDto);

		when(userServiceMock.updateUserById(Mockito.anyString(), Mockito.any())).thenThrow(UserDoesNotExistException.class);
		mvc.perform(put("/users/username1").param("userName", "username1").contentType(MediaType.APPLICATION_JSON)
				.content(userDtoJson)).andExpect(status().isNotFound());
	}
	
	@Test
	void testUpdateuserByIdInvalidUsername() throws Exception {

		UserDto userDto = new UserDto();
		userDto.setUserName("username1");

		String userDtoJson = new ObjectMapper().writeValueAsString(userDto);

		when(userServiceMock.updateUserById(Mockito.anyString(), Mockito.any())).thenThrow(UserDoesNotExistException.class);
		mvc.perform(put("/users/username1").param("userName", "username1").contentType(MediaType.APPLICATION_JSON)
				.content(userDtoJson)).andExpect(status().isNotFound());
	}


}

