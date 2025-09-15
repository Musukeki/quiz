package com.example.quiz.service.ifs;

import com.example.quiz.entity.User;
import com.example.quiz.vo.AddInfoReq;
import com.example.quiz.vo.BasicRes;
import com.example.quiz.vo.LoginReq;
import com.example.quiz.vo.LoginRes;

public interface UserService {

	public BasicRes addInfo(AddInfoReq req);
	
	public BasicRes login(LoginReq req);
	
//	public User getByEmail(String email);
	
	LoginRes loginAndGetAll(LoginReq req); // 新的，回帶使用者資料

}
