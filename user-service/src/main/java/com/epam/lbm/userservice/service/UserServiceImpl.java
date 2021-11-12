package com.epam.lbm.userservice.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.lbm.userservice.dto.UserDto;
import com.epam.lbm.userservice.entity.User;
import com.epam.lbm.userservice.exceptions.UserDoesNotExistException;
import com.epam.lbm.userservice.exceptions.DuplicateEntryException;
import com.epam.lbm.userservice.exceptions.NoUsersAvailableException;
import com.epam.lbm.userservice.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	ModelMapper modelMapper = new ModelMapper();
	
	@Override
	public User saveUser(UserDto userDto) throws DuplicateEntryException {
		if (userRepository.existsById(userDto.getUserName())) {
			throw new DuplicateEntryException("Username already exists!");
		}
		
		User user = modelMapper.map(userDto, User.class);
		userRepository.save(user);
		
		return user;
	}
	
	@Override
	public List<User> findAllUsers() throws NoUsersAvailableException {
		List<User> users = userRepository.findAll();
		
		if (users.isEmpty()) {
			throw new NoUsersAvailableException("No Users available!");
		}
		
		return users;
	}
	
	@Override
	public User findUserById(String userName) throws UserDoesNotExistException {
		Optional<User> userOptional = userRepository.findById(userName);
		
		if (!userOptional.isPresent()) {
			throw new UserDoesNotExistException("User does not exist!");
		}
		
		return userOptional.get();
	}
	
	@Override
	public String updateUserById(String userName, UserDto userDto) throws UserDoesNotExistException {
		Optional<User> userOptional = userRepository.findById(userName);
		
		if (!userOptional.isPresent()) {
			throw new UserDoesNotExistException("User does not exist!");
		}
		
		User user = userOptional.get();
		user.setUserName(userName);
		
		if (userDto.getName() != null) {
			user.setName(userDto.getName());
		}
		
		if (userDto.getEmail() != null) {
			user.setEmail(userDto.getEmail());
		}
		
		userRepository.save(user);
		return "User updated successfully";
	}
	
	@Override
	public String deleteUserById(String userName) throws UserDoesNotExistException {
		Optional<User> userOptional = userRepository.findById(userName);
		
		if (!userOptional.isPresent()) {
			throw new UserDoesNotExistException("Book does not exist!");
		}
		
		userRepository.deleteById(userName);
		return "User deleted successfully";
	}
}
