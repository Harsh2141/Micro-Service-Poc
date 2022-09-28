package com.tender247.poc.entity;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Users extends BaseEntity {

	private String email;
	private String username;
	private String password;
	private boolean enabled;
	private String role;
}
