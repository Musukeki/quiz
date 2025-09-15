package com.example.quiz.vo;

import java.util.List;

// 一個 StatisticsVo 表示 一題的所有選項統計
public class StatisticsVo {

	private int questionId;

	private String question;

	private String type;

	private boolean required;

	private List<OptionCountVo> optionCountVoList;

	public StatisticsVo() {
		super();
	}

	public StatisticsVo(int questionId, String question, String type, boolean required,
			List<OptionCountVo> optionCountVoList) {
		super();
		this.questionId = questionId;
		this.question = question;
		this.type = type;
		this.required = required;
		this.optionCountVoList = optionCountVoList;
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

	public List<OptionCountVo> getOptionCountVoList() {
		return optionCountVoList;
	}

	public void setOptionCountVoList(List<OptionCountVo> optionCountVoList) {
		this.optionCountVoList = optionCountVoList;
	}

}
