package com.papple.blog.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginRequest {
	@NotBlank
	private String email;

	private int type;

	@NotBlank
	private String password;
}
