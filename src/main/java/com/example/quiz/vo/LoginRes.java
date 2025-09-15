package com.example.quiz.vo;

public class LoginRes {

	private String code;
    private String message;
    private UserInfoRes user; // 專門放登入後的使用者資訊
    
	public LoginRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoginRes(String code, String message, UserInfoRes user) {
		super();
		this.code = code;
		this.message = message;
		this.user = user;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public UserInfoRes getUser() {
		return user;
	}

	public void setUser(UserInfoRes user) {
		this.user = user;
	}
	
	
    
    
}
