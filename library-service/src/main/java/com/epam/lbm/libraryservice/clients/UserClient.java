package com.epam.lbm.libraryservice.clients;

import java.util.List;

import javax.validation.Valid;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.epam.lbm.libraryservice.db.bean.User;

@FeignClient(name = "users-microservice", url="http://localhost:8082")
@LoadBalancerClient(name = "users-microservice")
public interface UserClient {

	@GetMapping("/users")
	public List<User> getAllUsers();
	
	@GetMapping("/users/{user-name}")
	public User getUser(@PathVariable("user-name") String userName);
	
	@PostMapping("/users")
	public String saveUser(@RequestBody @Valid User user);
	
	@PutMapping("/users/{user-name}")
	public String updateUser(@PathVariable("user-name") String userName, @RequestBody @Valid User user);
	
	@DeleteMapping("/users/{user-name}")
	public String deleteUser(@PathVariable("user-name") String userName);
}
