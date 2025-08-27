package com.example.quiz.constants;

public enum QuestionType {
	
	SINGLE("Single"),
	MULTI("Multi"),
	TEXT("Text");
	
	private String type;

	private QuestionType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
