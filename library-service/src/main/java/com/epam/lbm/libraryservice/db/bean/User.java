package com.epam.lbm.libraryservice.db.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="user")
@Getter
@Setter
public class User {
	@Id
	@Column(name="user_name")
	private String userName;
	
	@Column(name="name")
	private String name;
	
	@Column(name="email")
	private String email;
}
