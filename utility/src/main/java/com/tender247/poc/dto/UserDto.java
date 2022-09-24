package com.tender247.poc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class UserDto extends BaseDto {
	
	private String email;
	private String userName;
	private String password;
	private boolean enabled;
	private String role;
}
