package com.example.quiz.vo;

import java.util.List;

import com.example.quiz.constants.ConstantsMessage;

import jakarta.validation.constraints.*;

// 一個 QuestionVo 代表一題
public class QuestionVo {

	// 此屬性不檢查
	// QuestionVo 被使用在 QuizCreateReq 和 QuestionRes 2個類別中，
	// 當使用在 QuizCreateReq 時此屬性無作用，因問卷編號是自動生成的流水號；
	// 當使用在 QuestionRes 時則必須要有問卷編號的值
	private int quizId;
	
	@Min(value = 1, message = ConstantsMessage.QUESTION_ID_ERROR)
	private int questionId;

	@NotBlank(message = ConstantsMessage.QUESTION_ERROR)
	private String question;

	@NotBlank(message = ConstantsMessage.QUESTION_TYPE_ERROR)
	private String type;

	private boolean required;

	// 不檢查，因為有可能是簡答題，而簡答題不會有選項
	private List<String> options;
	
	public QuestionVo() {
		super();
	}
	
	public QuestionVo(int questionId, String question, String type, boolean required, List<String> options) {
		super();
		this.questionId = questionId;
		this.question = question;
		this.type = type;
		this.required = required;
		this.options = options;
	}
	

	public QuestionVo(int quizId, int questionId, String question, String type, boolean required, List<String> options) {
		super();
		this.quizId = quizId;
		this.questionId = questionId;
		this.question = question;
		this.type = type;
		this.required = required;
		this.options = options;
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

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

	public int getQuizId() {
		return quizId;
	}

	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

	
	

}
