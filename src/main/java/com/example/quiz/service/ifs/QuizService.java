package com.example.quiz.service.ifs;

import com.example.quiz.vo.BasicRes;
import com.example.quiz.vo.QuestionRes;
import com.example.quiz.vo.QuizCreateReq;
import com.example.quiz.vo.QuizUpdateReq;
import com.example.quiz.vo.SearchReq;
import com.example.quiz.vo.SearchRes;

public interface QuizService {

	public BasicRes create(QuizCreateReq req) throws Exception;
	
	public BasicRes update(QuizUpdateReq req) throws Exception;
	
	public SearchRes getAllQuizs();
	
	public QuestionRes getQuizsByQuizId(int quizId);

	
	public SearchRes search(SearchReq req);
	
	public BasicRes delete(int quizId) throws Exception;
}
