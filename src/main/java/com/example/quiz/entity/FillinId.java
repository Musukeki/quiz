package com.example.quiz.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FillinId implements Serializable {
	
	private int quizId;
	
	private int questionId;
	
	private String email;

	public int getQuizId() {
		return quizId;
	}

	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
