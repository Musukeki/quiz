package com.example.quiz.vo;

import java.util.List;

public class QuestionRes extends BasicRes {

	private List<QuestionVo> questionVoList;

	public QuestionRes() {
		super();

	}

	public QuestionRes(int code, String message) {
		super(code, message);

	}

	public QuestionRes(int code, String message, List<QuestionVo> questionVoList) {
		super(code, message);
		this.questionVoList = questionVoList;
	}

	public List<QuestionVo> getQuestionVoList() {
		return questionVoList;
	}

	public void setQuestionVoList(List<QuestionVo> questionVoList) {
		this.questionVoList = questionVoList;
	}

}
