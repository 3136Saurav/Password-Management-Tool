package com.epam.lbm.userservice.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UserTest {

	User user = new User();
	
	@Test
	void testUserName() {
		user.setUserName("username1");
		assertEquals("username1", user.getUserName());
	}
	
	@Test
	void testName() {
		user.setName("Full Name");
		assertEquals("Full Name", user.getName());
	}
	
	@Test
	void testEmail() {
		user.setEmail("email@gmail.com");
		assertEquals("email@gmail.com", user.getEmail());
	}

}
