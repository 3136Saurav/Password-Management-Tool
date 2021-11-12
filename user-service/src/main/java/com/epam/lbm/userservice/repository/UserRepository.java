package com.epam.lbm.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.lbm.userservice.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
