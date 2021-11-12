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

import com.epam.lbm.libraryservice.db.bean.Book;

@FeignClient(name = "books-microservice", url="http://localhost:8081")
@LoadBalancerClient(name = "books-microservice")
public interface BookClient {
	
	@GetMapping("/books")
	public List<Book> getAllBooks();
	
	@GetMapping("/books/{id}")
	public Book getBook(@PathVariable Long id);
	
	@PostMapping("/books")
	public String saveBook(@RequestBody @Valid Book book);
	
	@PutMapping("/books/{id}")
	public String updateBook(@PathVariable Long id, @RequestBody @Valid Book book);
	
	@DeleteMapping("/books/{id}")
	public String deleteBook(@PathVariable Long id);
}
