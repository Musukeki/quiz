package com.example.quiz.vo;

import java.util.List;

import com.example.quiz.entity.Quiz;

public class SearchRes extends BasicRes {

	private Quiz quiz;
	
	public SearchRes(int code, String message,List<?> questionList) {
		super(code, message);
		this.questionList = questionList;
	}

	private List<?> questionList;

	public SearchRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SearchRes(int code, String message) {
		super(code, message);
		// TODO Auto-generated constructor stub
	}

	public SearchRes(int code, String message, Quiz quiz, List<?> questionList) {
		super(code, message);
		this.quiz = quiz;
		this.questionList = questionList;
	}

	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	public List<?> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<?> questionList) {
		this.questionList = questionList;
	}
	
	
	
	



	

	
	
}
