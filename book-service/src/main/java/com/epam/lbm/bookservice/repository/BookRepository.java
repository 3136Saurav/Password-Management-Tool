package com.epam.lbm.bookservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.lbm.bookservice.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
