package com.example.quiz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz.service.ifs.QuizService;
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

import jakarta.validation.Valid;

@CrossOrigin
@RestController
public class QuizServiceController {

	@Autowired
	private QuizService quizService;

	@PostMapping("quiz/create")
	public BasicRes create(@RequestBody @Valid QuizCreateReq req) throws Exception {
		return quizService.create(req);
	}

	@PostMapping("quiz/update")
	public BasicRes update(@RequestBody @Valid QuizUpdateReq req) throws Exception {
		return quizService.update(req);
	}

	@GetMapping("quiz/getAll")
	public SearchRes getAllQuizs() {
		return quizService.getAllQuizs();
	}

	// API 的路徑: http://localhost:8080/quiz/get_questions?quizId=1
	// ?後面的 quizId 必須要和 @RequestParam 括號中的字串一樣
	@PostMapping("quiz/get_questions")
	public QuestionRes getQuizsByQuizId(@RequestParam("quizId") int quizId) {
		return quizService.getQuizsByQuizId(quizId);
	}

	@PostMapping("quiz/search")
	public SearchRes search(@RequestBody SearchReq req) {
		return quizService.search(req);
	}

	// 單筆刪除
//	 API 的路徑: http://localhost:8080/quiz/get_questions?quizId=1
//	 ?後面的 quizId 必須要和 @RequestParam 括號中的字串一樣
//	@PostMapping("quiz/delete")
//	public BasicRes delete(@RequestParam("quizId") int quizId) throws Exception {
//		return quizService.delete(quizId);
//	}

	@PostMapping("quiz/delete")
	public BasicRes delete(@RequestParam("quizId") List<Integer> quizIds) throws Exception {
		return quizService.deleteCheck(quizIds);
	}
	
	@GetMapping("quiz/get_quiz")
	public SearchRes getQuizById(@RequestParam("quizId") int quiz) throws Exception {
		return quizService.getQuizById(quiz);
	}
	
	// 填寫問卷
	@PostMapping("quiz/fillin")
	public BasicRes fillin(@RequestBody @Valid FillinReq req) throws Exception {
		return quizService.fillin(req);
	}
	
	@PostMapping("quiz/get_feedback_user_list")
	public FeedbackUserRes feedbackUserList(@RequestParam("quizId") int quizId) {
		return quizService.feedbackUserList(quizId);
	}
	
	@PostMapping("quiz/feedback")
	public FeedbackRes feedback(@RequestParam("quizId") int quizId, //
			@RequestParam("email") String email) {
		return quizService.feedback(quizId, email);
	}
	
	@PostMapping("quiz/statistics")
	public StatisticsRes statistics(@RequestParam("quizId") int quizId) {
		return quizService.statistics(quizId);
	}

	
	
	

}
