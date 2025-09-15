package com.example.quiz.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@IdClass(value = FillinId.class)
@Entity
@Table(name = "fillin")
public class Fillin {

	@Id
	@Column(name = "quiz_id")
	private int quizId;
	
	@Id
	@Column(name = "question_id")
	private int questionId;

	@Id
	@Column(name = "email")
	private String email;

	@Column(name = "answer")
	private String answer;

	@Column(name = "fillin_date")
	private LocalDate fillinDate;

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

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public LocalDate getFillinDate() {
		return fillinDate;
	}

	public void setFillinDate(LocalDate fillinDate) {
		this.fillinDate = fillinDate;
	}

}
