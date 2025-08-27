package com.example.quiz.service.ifs;

import com.example.quiz.vo.AddInfoReq;
import com.example.quiz.vo.BasicRes;
import com.example.quiz.vo.LoginReq;

public interface UserService {

	public BasicRes addInfo(AddInfoReq req);
	
	public BasicRes login(LoginReq req);

}
