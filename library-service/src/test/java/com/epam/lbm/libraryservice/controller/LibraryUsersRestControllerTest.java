package com.epam.lbm.libraryservice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import com.epam.lbm.libraryservice.LibraryServiceApplication;
import com.epam.lbm.libraryservice.clients.UserClient;
import com.epam.lbm.libraryservice.db.bean.User;
import com.epam.lbm.libraryservice.service.LibraryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest(classes = { LibraryServiceApplication.class })
class LibraryUsersRestControllerTest {
	@MockBean
	LibraryServiceImpl libraryService;

	@Autowired
	MockMvc mvc;
	
	@MockBean
	RestTemplate restTemplateMock;
	
	@MockBean
	UserClient userClientMock;
	
	@Mock
	ModelMapper modelMapper;

	@Test
	void testGetAllUsers() throws Exception {
		List<User> users = new ArrayList<>();
		User user = new User();
		user.setUserName("Aang");
		users.add(user);
		
		when(userClientMock.getAllUsers()).thenReturn(users);
		
		String usersListJson = new ObjectMapper().writeValueAsString(users);
		mvc.perform(get("/library/users/").contentType(MediaType.APPLICATION_JSON)).andExpect(content().string(usersListJson)).andExpect(status().isOk());
	}

	@Test
	void testGetUser() throws Exception {
	    User user = new User();
	    user.setUserName("Aang");
		when(userClientMock.getUser(user.getUserName())).thenReturn(user);

		String userJson = new ObjectMapper().writeValueAsString(user);
		
		mvc.perform(get("/library/users/Aang").contentType(MediaType.APPLICATION_JSON)).andExpect(content().string(userJson)).andExpect(status().isOk());
	}
	
	@Test
	void testAddUser() throws Exception {
		User user = new User();
		user.setUserName("Aang");
		user.setName("ABC");
		user.setEmail("aang@nick.com");

		String userJson = new ObjectMapper().writeValueAsString(user);
		when(userClientMock.saveUser(user)).thenReturn("User Added Successfully");

		mvc.perform(post("/library/users").contentType(MediaType.APPLICATION_JSON).content(userJson)).andExpect(status().isCreated());	
	}


	@Test
	void testUpdateUser() throws Exception {
		User user = new User();
		user.setUserName("Aang");
		when(userClientMock.updateUser(user.getUserName(), user)).thenReturn("User updated successfully");
		
		String userJson = new ObjectMapper().writeValueAsString(user);
		mvc.perform(put("/library/users/Aang").contentType(MediaType.APPLICATION_JSON).content(userJson)).andExpect(status().isOk());
	}

	@Test
	void testDeleteUser() throws Exception {
		User user = new User();
		user.setUserName("Aang");

		when(userClientMock.deleteUser(user.getUserName())).thenReturn("User deleted successfully");
		
		String userJson = new ObjectMapper().writeValueAsString(user);
		mvc.perform(delete("/library/users/Aang").contentType(MediaType.APPLICATION_JSON).content(userJson)).andExpect(status().isOk());
	}
}


