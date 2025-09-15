package com.example.quiz.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.quiz.entity.Quiz;

import jakarta.transaction.Transactional;

@Repository
public interface QuizDao extends JpaRepository<Quiz, Integer> {
	
	@Transactional
	@Modifying
	@Query(value = "insert into quiz (name, description, start_date, "//
			+ " end_date, is_published) values (?1, ?2, ?3, ?4, ?5)", //
			nativeQuery = true)
	public void insert(String name, String description,//
			LocalDate startDate, LocalDate endDate, boolean published);
	
	@Query(value = "select max(id) from quiz.quiz", nativeQuery = true)
	public int getLatestQuizId();
	
	@Query(value = "select count(id) from quiz.quiz where id = ?1", nativeQuery = true)
	public int getCountByQuizId(int quizId);
	
	/**
	 * 回傳值的資料型態設定成 int 主要是用來判斷資料是否有正確更新成功
	 * int = 1 表示有資料被更新；0 表示無資料被更新
	 */
	@Transactional
	@Modifying
	@Query(value = "update quiz set name = ?2, description = ?3, start_date = ?4, "//
			+ " end_date = ?5, is_published = ?6 where id = ?1", //
			nativeQuery = true)
	public int update(int id, String name, String description,//
			LocalDate startDate, LocalDate endDate, boolean published);
	
	@Query(value = "select * from quiz", //
			nativeQuery = true)
	public List<Quiz> getAll();
	
	@Query(value = "select * from quiz where name like %?1% and start_date >= ?2 "//
			+ " end_date <= ?3", //
			nativeQuery = true)
	public List<Quiz> getAll(String name, LocalDate startDate, LocalDate endDate);
	
	// is_published is true: is 只能用在 boolean 值的欄位，is 也可以替換成(=)
	@Query(value = "select * from quiz where name like %?1% and start_date >= ?2 "//
			+ " end_date <= ?3 and is_published is true", //
			nativeQuery = true)
	public List<Quiz> getAllPublished(String name, LocalDate startDate, LocalDate endDate);
	
	@Transactional
	@Modifying
	@Query(value = "delete from quiz where id = ?1", //
			nativeQuery = true)
	public void deleteById(int id);
	
	@Query(value = "select * from quiz where id = ?1", //
			nativeQuery = true)
	public Quiz getById(int id);


	// ============= 以下死三代碼
	
	@Query(value = "select name, description from quiz where id = ?1", //
			nativeQuery = true)
	public List<Quiz> getQuizAndQuesById(int id);
	
	// 判斷該問卷是否已發布及當下的日期是否介於開始日期和結束日期之間
	@Query(value = "select count(id) from quiz where id = ?1 and ?2 >= start_date "//
			+ " and ?2 <=  end_date and is_published is true", //
			nativeQuery = true)
	public int selectCountById(int id, LocalDate now);
	
	
	
	
	
	
	
	
	
	
	
	
	
}
