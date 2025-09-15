package com.example.quiz.constants;

public enum ResCodeMessage {

	 SUCCESS(200, "Success!!"), //
	 PASSWORD_ERROR(400, "Password Error!!"), //
	 EMAIL_EXISTS(400, "Account Exists!!"), //
	 NOT_FOUND(404, "Not Found!!"), //
	 PASSWORD_MISMATCH(400, "Password Mismatch!!"), //
	 QUIZ_CREATE_ERROR(400, "Quiz Create Error"), //
	 ADD_INFO_ERROR(400, "Add Info Error"), //
	 DATE_FORMAT_ERROR(400, "Date Format Error"), //
	 QUESTION_TYPE_ERROR(400, "Question Type Error"), //
	 OPTIONS_INSUFFICIENT(400, "Options Insufficient!!"),//
	 TEXT_HAS_OPTIONS_ERROR(400, "Text Has Options Error!!"),
	 QUIZ_UPDATE_FAILED(400, "Quiz Update Failed!!"), //
	 OPTIONS_TRANSFER_ERROR(400, "Options Transfer Error!!"),
	 QUIZ_ID_ERROR(400, "Quiz Id Error!!"),//
	 QUIZ_DELETE_FAILED(400, "Quiz Delete Error!!"),//
	 QUIZ_CANNOT_BE_EDITED(400, "Quiz Cannot Be Edited!!"),//
	 QUIZ_STARTED_NO_DELETE(400, "Quiz Already Started, Can't Delete"),//
	 ANSWER_REQUIRED(400, "Answer Required!!"),//
	 QUESTION_TYPE_IS_SINGLE(400, "Question Type Is Single!!"),//
	 OPTION_ANSWER_MISSMATCH(400, "Option Answer Missmatch!!"),//
	 EMAIL_DUPLICATED(400, "Email Duplicated!!"),//
	 OBJECT_MAPPER_PROCESSING_ERROR(400, "Object Mapper Processing Error!!"),//
	 
	;
	private int code;

	public int getCode() {
		return code;
	}

	// enum 沒有預設建構方法
	// 帶有參數建構方法一定要有
	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private String message;

	private ResCodeMessage(int code, String message) {
		this.code = code;
		this.message = message;
	}

}
