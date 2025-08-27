package com.example.quiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz.service.ifs.UserService;
import com.example.quiz.vo.AddInfoReq;
import com.example.quiz.vo.BasicRes;
import com.example.quiz.vo.LoginReq;

import jakarta.validation.Valid;

/**
 * 可提供跨域資源共享的請求(雖然前後端系統都在自己的電腦，
 * 但前端呼叫後端的 API 時，API 會被認為是跨域請求)
 */
@CrossOrigin
@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping(value = "user/add_info")
	public BasicRes AddInfo(@Valid @RequestBody AddInfoReq req) {
		BasicRes res = userService.addInfo(req);
		return res;
	}
	
	@PostMapping(value = "user/login")
	public BasicRes Login(@RequestBody LoginReq req) {
		return userService.login(req);
	}
	
	
}
