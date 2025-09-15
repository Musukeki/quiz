package com.example.quiz.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.quiz.entity.Fillin;
import com.example.quiz.entity.FillinId;
import com.example.quiz.vo.QuestionAnswerDto;
import com.example.quiz.vo.UserVo;

import jakarta.transaction.Transactional;


@Repository
public interface FillinDao extends JpaRepository<Fillin, FillinId> {

	@Transactional
	@Modifying
	@Query(value = "insert into fillin (quiz_id, question_id, email, answer, " //
			+ " fillin_date) values (?1, ?2, ?3, ?4, ?5)", //
			nativeQuery = true)
	public void insert(int quizId, int questionId, String email, String answer, LocalDate now);
	
	@Query(value = "select count(email) from fillin where quiz_id = ?1 " //
			+ " and email = ?2", //
			nativeQuery = true)
	public int selectCountByQuizIdAndEmail(int quizId, String email);
	
	@Query(value = "select distinct new com.example.quiz.vo.UserVo( " //
			+ " U.name, U.email, U.phone, U.age, F.fillinDate) from User as U " //
			+ " join Fillin as F on U.email = F.email where F.quizId = ?1", //
			nativeQuery = false)
	public List<UserVo> selectUserVoList(int quizId);
	
	@Query(value = "select new com.example.quiz.vo.QuestionAnswerDto(" //
			+ " Qu.questionId, Qu.question, Qu.type, Qu.required, F.answer) " //
			+ " from Question as Qu left join Fillin as F " //
			+ " on Qu.questionId = F.questionId and F.quizId = ?1 and F.email = ?2 "
			+ " where Qu.quizId = ?1 ", //
			nativeQuery = false)
	public List<QuestionAnswerDto> selectQuestionAnswerList(int quizId, String email);
	

	@Query(value = "select new com.example.quiz.vo.QuestionAnswerDto(" //
			+ " Qu.questionId, Qu.question, Qu.type, Qu.required, F.answer) " //
			+ " from Question as Qu join Fillin as F " //
			+ " on Qu.questionId = F.questionId and F.quizId = ?1 " //
			+ " where Qu.quizId = ?1 ", //
			nativeQuery = false)
	public List<QuestionAnswerDto> selectQuestionAnswerList(int quizId);
}
