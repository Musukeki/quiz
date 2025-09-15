package com.example.quiz.vo;

import java.util.List;

import com.example.quiz.constants.ConstantsMessage;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class FillinReq {

	@Min(value = 1, message = ConstantsMessage.QUIZ_ID_ERROR)
	private int quizId;
	
	@NotBlank(message = ConstantsMessage.EMAIL_ERROR)
	private String email;
	
	@Valid // 因為 vo 裡面的 questionId 有驗證，所以要加上 @Valid 才會讓驗證生效
	private List<QuestionIdAnswerVo> questionAnswerVoList;

	public int getQuizId() {
		return quizId;
	}

	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<QuestionIdAnswerVo> getQuestionAnswerVoList() {
		return questionAnswerVoList;
	}

	public void setQuestionAnswerVoList(List<QuestionIdAnswerVo> questionAnswerVoList) {
		this.questionAnswerVoList = questionAnswerVoList;
	}
	
	
}
