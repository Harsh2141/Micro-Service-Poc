package com.tender247.poc.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserUpdateRequest {

    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 15)
    private String userName;

    @Email
    private String email;
}
