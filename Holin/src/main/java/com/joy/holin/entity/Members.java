package com.joy.holin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "members")
@Data
public class Members {
	
	@Id
	private Long id;
	private String email;
	private String pwd;
	private String name;
	private String phone;
	private String address;
	
	
}
