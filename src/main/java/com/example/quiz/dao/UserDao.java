package com.example.quiz.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.quiz.entity.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserDao extends JpaRepository<User, String> {
	
	//===== 登入 =====
	// select count(欄位) from 表 where 條件: 用來計算該欄位的值滿足條件的筆數，不存在會回傳 0
	@Query(value = "SELECT count(email) from user where email = ?1", nativeQuery = true)
	public int getCountByEmail(String email);
	
	@Query(value = "SELECT * from user where email = ?1", nativeQuery = true)
	public User getByEmail(String email);
	
	//===== 新增 =====
	@Transactional
	@Modifying
	@Query(value = "INSERT into user(name, phone, email, age, password) values "//
			+ " (?1, ?2, ?3, ?4, ?5) ",
			nativeQuery = true)
	public void addInfo(String name, String phone, String email, int age, String password);
	

}
