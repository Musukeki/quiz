package com.example.quiz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.quiz.constants.ResCodeMessage;
import com.example.quiz.dao.UserDao;
import com.example.quiz.entity.User;
import com.example.quiz.service.ifs.UserService;
import com.example.quiz.vo.AddInfoReq;
import com.example.quiz.vo.BasicRes;
import com.example.quiz.vo.LoginReq;
import com.example.quiz.vo.LoginRes;
import com.example.quiz.vo.UserInfoRes;

@Service
public class UserServiceImpl implements UserService {
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@Autowired
	private UserDao userDao;

	@Override
	public BasicRes addInfo(AddInfoReq req) {
		
		// req 的參數檢查已經在類別 User 中透過 Validation 驗證，此處不需要額外透過程式碼驗證
		
		// 1. 檢查 email 是否已存在
		// 透過 PK 去取得 count 數，只會得到 0 或 1
		int count = userDao.getCountByEmail(req.getEmail());
		if(count == 1) { // email 是 PK，若 count = 1 表示帳號已存在
			return new BasicRes(ResCodeMessage.EMAIL_EXISTS.getCode(),//
					 ResCodeMessage.EMAIL_EXISTS.getMessage());
		}
		
		// 2. 新增資訊
		try {
			userDao.addInfo(req.getName(), req.getPhone(), //		
					req.getEmail(), req.getAge(), encoder.encode(req.getPassword()));
			return new BasicRes(ResCodeMessage.SUCCESS.getCode(),//
					 ResCodeMessage.SUCCESS.getMessage());
		} catch (Exception e) {
			return new BasicRes(ResCodeMessage.ADD_INFO_ERROR.getCode(),//
					 ResCodeMessage.ADD_INFO_ERROR.getMessage());
		}
	}

	@Override
	public BasicRes login(LoginReq req) {
		// 確認 email 是否存在資料庫
		User user = userDao.getByEmail(req.getEmail());
		
		// 透過 email 取得一筆資料，email 不存在的話，會得到 null
		if(user == null) {
			return new BasicRes(ResCodeMessage.NOT_FOUND.getCode(),//
					 ResCodeMessage.NOT_FOUND.getMessage());
		}
		if(!encoder.matches(req.getPassword(), user.getPassword())) {
			return new BasicRes(ResCodeMessage.PASSWORD_MISMATCH.getCode(),//
					 ResCodeMessage.PASSWORD_MISMATCH.getMessage());
		}
		return new BasicRes(ResCodeMessage.SUCCESS.getCode(),//
				 ResCodeMessage.SUCCESS.getMessage());
	}


	@Override
	public LoginRes loginAndGetAll(LoginReq req) {
		User user = userDao.getByEmail(req.getEmail());
	    if (user == null) {
	        return new LoginRes("404", "帳號不存在", null);
	    }
	    if (!encoder.matches(req.getPassword(), user.getPassword())) {
	        return new LoginRes("401", "密碼錯誤", null);
	    }

	    UserInfoRes info = new UserInfoRes();
	    info.setName(user.getName());
	    info.setPhone(user.getPhone());
	    info.setEmail(user.getEmail());
	    info.setAge(user.getAge());

	    return new LoginRes("200", "登入成功", info);
	}



	
}
