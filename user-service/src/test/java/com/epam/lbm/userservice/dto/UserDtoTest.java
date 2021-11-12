package com.epam.lbm.userservice.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


class UserDtoTest {

	UserDto userDTO = new UserDto();
	
	@Test
	void testUserName() {
		userDTO.setUserName("username1");
		assertEquals("username1", userDTO.getUserName());
	}
	
	@Test
	void testName() {
		userDTO.setName("Full Name");
		assertEquals("Full Name", userDTO.getName());
	}
	
	@Test
	void testEmail() {
		userDTO.setEmail("email@gmail.com");
		assertEquals("email@gmail.com", userDTO.getEmail());
	}

}
