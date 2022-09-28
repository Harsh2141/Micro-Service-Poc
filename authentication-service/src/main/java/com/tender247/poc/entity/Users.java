package com.tender247.poc.entity;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Users extends BaseEntity {

	private String email;
	private String userName;
	private String password;
	private boolean enabled;
	private String role;
}
