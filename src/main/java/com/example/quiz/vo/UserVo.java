package com.example.quiz.vo;

import java.time.LocalDate;

public class UserVo {

	private String name;

	private String email;

	private String phone;

	private int age;

	private LocalDate fillinDate;

	public UserVo() {
		super();
	}

	public UserVo(String name, String email, String phone, int age, LocalDate fillinDate) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.age = age;
		this.fillinDate = fillinDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getFillinDate() {
		return fillinDate;
	}

	public void setFillinDate(LocalDate fillinDate) {
		this.fillinDate = fillinDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
