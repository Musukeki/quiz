package com.example.quiz.entity;

import java.io.Serializable;

@SuppressWarnings("serial") // 忽略警告(黃底線)
public class QuestionId implements Serializable { // 實作序列化(複合 PK)

	private int quizId;
	
	private int questionId;

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
	
	
}
