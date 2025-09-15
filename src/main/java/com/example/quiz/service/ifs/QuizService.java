package com.example.quiz.service.ifs;

import java.util.List;

import com.example.quiz.vo.BasicRes;
import com.example.quiz.vo.FeedbackRes;
import com.example.quiz.vo.FeedbackUserRes;
import com.example.quiz.vo.FillinReq;
import com.example.quiz.vo.QuestionRes;
import com.example.quiz.vo.QuizCreateReq;
import com.example.quiz.vo.QuizUpdateReq;
import com.example.quiz.vo.SearchReq;
import com.example.quiz.vo.SearchRes;
import com.example.quiz.vo.StatisticsRes;

public interface QuizService {

	public BasicRes create(QuizCreateReq req) throws Exception;

	public BasicRes update(QuizUpdateReq req) throws Exception;

	public SearchRes getAllQuizs();

	public QuestionRes getQuizsByQuizId(int quizId);

	public SearchRes search(SearchReq req);

	// 單筆刪除
	public BasicRes delete(int quizId) throws Exception;
	
	// 多筆刪除
	public BasicRes deleteCheck(List<Integer> quizIds) throws Exception;
	
	public SearchRes getQuizById(int quizId) throws Exception;
	
	public BasicRes fillin(FillinReq req) throws Exception;
	
	public FeedbackUserRes feedbackUserList(int quizId);
	
	public FeedbackRes feedback(int quizId, String email);
	
	public StatisticsRes statistics(int quizId);

}
