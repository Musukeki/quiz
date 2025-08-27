package com.example.quiz.vo;

import com.example.quiz.constants.ConstantsMessage;

import jakarta.validation.constraints.NotBlank;

public class LoginReq {

	@NotBlank(message = ConstantsMessage.EMAIL_ERROR)
	private String email;
	
	@NotBlank(message = ConstantsMessage.PASSWORD_ERROR)
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
