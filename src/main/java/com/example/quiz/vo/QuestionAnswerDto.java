package com.example.quiz.vo;

public class QuestionAnswerDto {

	private int questionId;

	private String question;

	private String type;

	private boolean required;

	private String answerStr;

	public QuestionAnswerDto() {
		super();
	}

	public QuestionAnswerDto(int questionId, String question, String type, boolean required, String answerStr) {
		super();
		this.questionId = questionId;
		this.question = question;
		this.type = type;
		this.required = required;
		this.answerStr = answerStr;
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

	public String getAnswerStr() {
		return answerStr;
	}

	public void setAnswerStr(String answerStr) {
		this.answerStr = answerStr;
	}

}
