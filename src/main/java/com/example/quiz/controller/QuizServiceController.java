package com.example.quiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz.service.ifs.QuizService;
import com.example.quiz.vo.BasicRes;
import com.example.quiz.vo.QuestionRes;
import com.example.quiz.vo.QuizCreateReq;
import com.example.quiz.vo.QuizUpdateReq;
import com.example.quiz.vo.SearchReq;
import com.example.quiz.vo.SearchRes;

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
	
	// API 的路徑: http://localhost:8080/quiz/get_questions?quizId=1
	// ?後面的 quizId 必須要和 @RequestParam 括號中的字串一樣
	@PostMapping("quiz/delete")
	public BasicRes delete(@RequestParam("quizId") int quizId) throws Exception {
		return quizService.delete(quizId);
	}
	
	
	
	
	
}
