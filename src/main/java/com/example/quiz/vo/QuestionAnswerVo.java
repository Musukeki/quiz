package com.example.quiz.vo;

import java.util.List;

public class QuestionAnswerVo {

	private int questionId;

	private String question;

	private String type;

	private boolean required;

	private List<String> answerList;

	public QuestionAnswerVo() {
		super();
	}

	public QuestionAnswerVo(int questionId, String question, String type, boolean required, List<String> answerList) {
		super();
		this.questionId = questionId;
		this.question = question;
		this.type = type;
		this.required = required;
		this.answerList = answerList;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public List<String> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(List<String> answerList) {
		this.answerList = answerList;
	}

}
