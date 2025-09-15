package com.example.quiz.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.quiz.constants.QuestionType;
import com.example.quiz.constants.ResCodeMessage;
import com.example.quiz.dao.FillinDao;
import com.example.quiz.dao.QuestionDao;
import com.example.quiz.dao.QuizDao;
import com.example.quiz.entity.Question;
import com.example.quiz.entity.Quiz;
import com.example.quiz.service.ifs.QuizService;
import com.example.quiz.vo.BasicRes;
import com.example.quiz.vo.FeedbackRes;
import com.example.quiz.vo.FeedbackUserRes;
import com.example.quiz.vo.FillinReq;
import com.example.quiz.vo.OptionCountVo;
import com.example.quiz.vo.QuestionAnswerDto;
import com.example.quiz.vo.QuestionAnswerVo;
import com.example.quiz.vo.QuestionIdAnswerVo;
import com.example.quiz.vo.QuestionRes;
import com.example.quiz.vo.QuestionVo;
import com.example.quiz.vo.QuizCreateReq;
import com.example.quiz.vo.QuizUpdateReq;
import com.example.quiz.vo.SearchReq;
import com.example.quiz.vo.SearchRes;
import com.example.quiz.vo.StatisticsRes;
import com.example.quiz.vo.StatisticsVo;
import com.example.quiz.vo.UserVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class QuizServiceImpl implements QuizService {

	// 提供 類別(或 JSON 格式)與物件之間的轉換
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private QuizDao quizDao;

	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private FillinDao fillinDao;

	/**
	 * @throws Exception
	 * @Transactional: 事務</br>
	 *                 1. 當一個方法中執行多個 Dao 時(跨表或是同一張表寫多筆資料)，這些所有的資料應該都要算同一次行為，
	 *                 所以這些資料不是全部寫入成功，就是全部寫入失敗</br>
	 *                 2. @Transactional 有效回溯的異常預設是 RunTimeException，若發生的異常不是
	 *                 RunTimeException
	 *                 或其子類別的異常類型，資料皆不會回溯，因此想要讓只要發生任何一種異常時資料都要可回溯，可以
	 *                 將 @Transactional 的有效範圍從 RunTimeException 提高至 Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public BasicRes create(QuizCreateReq req) throws Exception {
		// 參數檢查以透過 @Valid 驗證了
		try {
			// 檢查日期
			BasicRes checkRes = checkDate(req.getStartDate(), req.getEndDate());
			if (checkRes.getCode() != 200) { // 不等於 200 表示檢查出有錯誤
				return checkRes;
			}

			// 新增問卷
			quizDao.insert(req.getName(), req.getDescription(), //
					req.getStartDate(), req.getEndDate(), req.isPublished());
			// 雖然因為 @Transactional 尚未將資料提交(commit)進資料庫，但實際上SQL語法已經執行完畢，
			// 依然可以取得對應的值
			int quizId = quizDao.getLatestQuizId();
			// 新增完問卷後，取得問卷流水號
			// 新增問卷問題
			// 取出問卷中的所有問題
			List<QuestionVo> questionVoList = req.getQuestionList();
			// 處理每一題問題
			for (QuestionVo vo : questionVoList) {
				// 檢查題目類型與選項
				checkRes = checkQuestionType(vo);
				// 呼叫方法 checkQuestionType 得到的 res 若是 null，表示檢查都沒問題，
				// 因為方法中檢查到最後都沒有問題時，是回傳 成功
				if (checkRes.getCode() != 200) {
//					return checkRes;
					// 因為前面已經執行了 quizDao.insert 了，所以這邊要拋出 Exception
					// 才會讓 @Transactional 生效
					throw new Exception(checkRes.getMessage());
				}
				// 因為 MySQL 沒有 List 的資料格式，所以要把 options 資料格式 從 List<String> 轉成 String
				List<String> optionsList = vo.getOptions();
				String str = mapper.writeValueAsString(optionsList);
				// 要記得設定 quizId
				questionDao.insert(quizId, vo.getQuestionId(), vo.getQuestion(), //
						vo.getType(), vo.isRequired(), str);
			}
			return new BasicRes(ResCodeMessage.SUCCESS.getCode(), //
					ResCodeMessage.SUCCESS.getMessage());
		} catch (Exception e) {
			// 不能 return BasicReq 而是要將發生的異常拋出去，這樣 @Transaction 才會生效
			throw e;
		}
	}

	private BasicRes checkQuestionType(QuestionVo vo) {
		// 1.檢查 type 是否是規定的類型
		String type = vo.getType();
		// 假設 從 vo 取出來的 type 不符合定義的 3 種類型的其中一種，就返回錯誤訊息
		if (!type.equalsIgnoreCase(QuestionType.SINGLE.getType())//
				&& !type.equalsIgnoreCase(QuestionType.MULTI.getType())//
				&& !type.equalsIgnoreCase(QuestionType.TEXT.getType())) {
			return new BasicRes(ResCodeMessage.QUESTION_TYPE_ERROR.getCode(), //
					ResCodeMessage.QUESTION_TYPE_ERROR.getMessage());
		}

		// 2. type 是單選或多選的時候，選項(options)只少要有 2 個
		// 假設 type 不等於 TEXT -> 就表示 type 是單選或多選
		if (!type.equalsIgnoreCase(QuestionType.TEXT.getType())) {
			// 單選或多選時，選項至少要有 2 個
			if (vo.getOptions().size() < 2) {
				return new BasicRes(ResCodeMessage.OPTIONS_INSUFFICIENT.getCode(), //
						ResCodeMessage.OPTIONS_INSUFFICIENT.getMessage());
			}
		} else { // else -> type 是 Text -> 選項應該是 null 或是 size = 0
			// 假設 選項不是 null 或 選項的 List 中有值
			if (vo.getOptions() != null && vo.getOptions().size() > 0) {
				return new BasicRes(ResCodeMessage.TEXT_HAS_OPTIONS_ERROR.getCode(), //
						ResCodeMessage.TEXT_HAS_OPTIONS_ERROR.getMessage());
			}
		}
		return new BasicRes(ResCodeMessage.SUCCESS.getCode(), //
				ResCodeMessage.SUCCESS.getMessage());
	}

	private BasicRes checkDate(LocalDate startDate, LocalDate endDate) {
		// 1. 開始日期不能比結束時間晚 2.開始日期不能比當前創見的日期早
		// 判斷式: 假設開始日期比結束日期晚 或 開始日期比當前日期早 -> 回傳錯誤訊息
		// LocalDate.now() -> 取得當前的日期
		if (startDate.isAfter(endDate) || //
				startDate.isBefore(LocalDate.now())) {
			return new BasicRes(ResCodeMessage.DATE_FORMAT_ERROR.getCode(), //
					ResCodeMessage.DATE_FORMAT_ERROR.getMessage());
		}
		return new BasicRes(ResCodeMessage.SUCCESS.getCode(), //
				ResCodeMessage.SUCCESS.getMessage());
	}

	private BasicRes checkStatus(LocalDate startDate, boolean isPublished) {
		// 允許問卷修改的狀態條件: 1.尚未發布 2.已發布+尚未開始
		if (!isPublished || startDate.isAfter(LocalDate.now())) {
			// 返回成功表示問卷允許被修改
			return new BasicRes(ResCodeMessage.SUCCESS.getCode(), //
					ResCodeMessage.SUCCESS.getMessage());
		}
		return new BasicRes(ResCodeMessage.QUIZ_CANNOT_BE_EDITED.getCode(), //
				ResCodeMessage.QUIZ_CANNOT_BE_EDITED.getMessage());
	}

//	@Override
//	public BasicRes update(QuizUpdateReq req) throws Exception {
//		// 參數檢查以透過 @Valid 驗證了
//
//		// 更新是對已存在的問卷進行修改
//		try {
//			// 1.檢查 QuizId 是否存在
//			int quizId = req.getQuizId();
//			int count = quizDao.getCountByQuizId(quizId);
//			if (count != 1) {
//				return new BasicRes(ResCodeMessage.NOT_FOUND.getCode(), //
//						ResCodeMessage.NOT_FOUND.getMessage());
//			}
//			// 2.檢查日期
//			BasicRes checkRes = checkDate(req.getStartDate(), req.getEndDate());
//			if (checkRes.getCode() != 200) { // 不等於 200 表示檢查出有錯誤
//				return checkRes;
//			}
//			// 3.更新問卷
//			int updateRes = quizDao.update(quizId, req.getName(), req.getDescription(), //
//					req.getStartDate(), req.getEndDate(), req.isPublished());
//			if (updateRes != 1) { // 表示資料沒更新成功
//				return new BasicRes(ResCodeMessage.QUIZ_UPDATE_FAILED.getCode(), //
//						ResCodeMessage.QUIZ_UPDATE_FAILED.getMessage());
//			}
//			// 4.刪除同一張問卷的所有問題
//			questionDao.deleteByQuizId(quizId);
//			// 5.檢查問題
//			List<QuestionVo> questionVoList = req.getQuestionList();
//			for (QuestionVo vo : questionVoList) {
//				// 檢查題目類型與選項
//				checkRes = checkQuestionType(vo);
//				// 方法中檢查到最後都沒有問題時，是回傳 成功
//				if (checkRes.getCode() != 200) {
//					// 因為前面已經執行了 quizDao.insert 了，所以這邊要拋出 Exception
//					// 才會讓 @Transactional 生效
//					throw new Exception(checkRes.getMessage());
//				}
//				// 因為 MySQL 沒有 List 的資料格式，所以要把 options 資料格式 從 List<String> 轉成 String
//				List<String> optionsList = vo.getOptions();
//				String str = mapper.writeValueAsString(optionsList);
//				// 要記得設定 quizId
//				questionDao.insert(quizId, vo.getQuestionId(), vo.getQuestion(), //
//						vo.getType(), vo.isRequired(), str);
//			}
//		} catch (Exception e) {
//			// 不能 return BasicReq 而是要將發生的異常拋出去，這樣 @Transaction 才會生效
//			throw e;
//		}
//		return new BasicRes(ResCodeMessage.SUCCESS.getCode(), //
//				ResCodeMessage.SUCCESS.getMessage());
//	}
	@Override
	public BasicRes update(QuizUpdateReq req) throws Exception {
		// 參數檢查以透過 @Valid 驗證了

		// 更新是對已存在的問卷進行修改
		try {
			// 1.檢查 QuizId 是否存在
			int quizId = req.getQuizId();
//			int count = quizDao.getCountByQuizId(quizId);
			// 不使用 count 數而是取出整筆資料的主要原因是因為後續還會使用到資料庫中的資料
			Quiz quiz = quizDao.getById(quizId);
			if (quiz == null) {
				return new BasicRes(ResCodeMessage.NOT_FOUND.getCode(), //
						ResCodeMessage.NOT_FOUND.getMessage());
			}
			// 2.檢查日期
			BasicRes checkRes = checkDate(req.getStartDate(), req.getEndDate());
			if (checkRes.getCode() != 200) { // 不等於 200 表示檢查出有錯誤
				return checkRes;
			}
			// 3.檢查原本的問卷狀態(相關欄位的值是存在DB中)是否可被更新
			checkRes = checkStatus(quiz.getStartDate(), quiz.isPublished());
			if (checkRes.getCode() != 200) { // 不等於 200 表示問卷不允許被更新
				return checkRes;
			}
			// 4.更新問卷
			int updateRes = quizDao.update(quizId, req.getName(), req.getDescription(), //
					req.getStartDate(), req.getEndDate(), req.isPublished());
			if (updateRes != 1) { // 表示資料沒更新成功
				return new BasicRes(ResCodeMessage.QUIZ_UPDATE_FAILED.getCode(), //
						ResCodeMessage.QUIZ_UPDATE_FAILED.getMessage());
			}
			// 5.刪除同一張問卷的所有問題
			questionDao.deleteByQuizId(quizId);
			// 6.檢查問題
			List<QuestionVo> questionVoList = req.getQuestionList();
			for (QuestionVo vo : questionVoList) {
				// 檢查題目類型與選項
				checkRes = checkQuestionType(vo);
				// 方法中檢查到最後都沒有問題時，是回傳 成功
				if (checkRes.getCode() != 200) {
					// 因為前面已經執行了 quizDao.insert 了，所以這邊要拋出 Exception
					// 才會讓 @Transactional 生效
					throw new Exception(checkRes.getMessage());
				}
				// 因為 MySQL 沒有 List 的資料格式，所以要把 options 資料格式 從 List<String> 轉成 String
				List<String> optionsList = vo.getOptions();
				String str = mapper.writeValueAsString(optionsList);
				// 要記得設定 quizId
				questionDao.insert(quizId, vo.getQuestionId(), vo.getQuestion(), //
						vo.getType(), vo.isRequired(), str);
			}
		} catch (Exception e) {
			// 不能 return BasicReq 而是要將發生的異常拋出去，這樣 @Transaction 才會生效
			throw e;
		}
		return new BasicRes(ResCodeMessage.SUCCESS.getCode(), //
				ResCodeMessage.SUCCESS.getMessage());
	}

	@Override
	public SearchRes getAllQuizs() {
		List<Quiz> list = quizDao.getAll();
		return new SearchRes(ResCodeMessage.SUCCESS.getCode(), //
				ResCodeMessage.SUCCESS.getMessage(), list);
	}

	@Override
	public QuestionRes getQuizsByQuizId(int quizId) {
		if (quizId <= 0) {
			return new QuestionRes(ResCodeMessage.QUIZ_ID_ERROR.getCode(), //
					ResCodeMessage.QUIZ_ID_ERROR.getMessage());
		}
		List<QuestionVo> questionVoList = new ArrayList<>();
		List<Question> list = questionDao.getQuestionsByQuizId(quizId);
		// 把每題選項的資料型態從 String 轉換成 List<String>
		for (Question item : list) {
			String str = item.getOptions();
			try {
				List<String> optionList = mapper.readValue(str, new TypeReference<>() {
				});
				// 將從DB取得的每一筆資料(Question item) 的每個欄位值放到 QuestionVo 中，以便返回給使用者
				// Question 和 QuestionVo 的差別在於 選項的資料型態
				QuestionVo vo = new QuestionVo(item.getQuizId(), item.getQuestionId(), //
						item.getQuestion(), item.getType(), item.isRequired(), optionList);
				// 把每個 vo 放到 questionList 中
				questionVoList.add(vo);
			} catch (Exception e) {
				// 這邊不寫 throw e 是因為次方法中沒有使用 @Transactional，不影響返回結果
				return new QuestionRes(ResCodeMessage.OPTIONS_TRANSFER_ERROR.getCode(), //
						ResCodeMessage.OPTIONS_TRANSFER_ERROR.getMessage());
			}
		}
		return new QuestionRes(ResCodeMessage.SUCCESS.getCode(), //
				ResCodeMessage.SUCCESS.getMessage(), questionVoList);
	}

	@Override
	public SearchRes search(SearchReq req) {
		// 轉換 req 的值
		// 若 quizName 是 null，轉成空字串
		String quizName = req.getQuizName();
//		if(quizName == null) {
//			quizName = "";
//		} else { // 多餘的，不需要寫，但為理解了下面的三元運算子而寫
//			quizName = quizName;
//		}
		// 三元運算
		// 格式：變數名稱 = 條件判斷式 ? 判斷式結果為 true 所賦予的值 : 判斷式結果為 false 所賦予的值
		quizName = quizName == null ? "" : quizName;
		// 上面的程式碼可以只用下面一行來取得
//		String quizName1 = req.getQuizName() == null ? "" : req.getQuizName();
		// ==================================================

		// 轉換 開始日期 -> 若沒有給開始日期 -> 給定一個很早的時間
		LocalDate startDate = req.getStartDate() == null ? LocalDate.of(1970, 1, 1) //
				: req.getStartDate();
		LocalDate endDate = req.getEndDate() == null ? LocalDate.of(2999, 12, 31) //
				: req.getEndDate();
		List<Quiz> list = new ArrayList<>();
		if (req.isPublished()) {
			list = quizDao.getAllPublished(quizName, startDate, endDate);
		} else {
			list = quizDao.getAll(quizName, startDate, endDate);
		}
		return new SearchRes(ResCodeMessage.SUCCESS.getCode(), //
				ResCodeMessage.SUCCESS.getMessage(), list);
	}

	// 單筆刪除
	@Transactional(rollbackFor = Exception.class)
	@Override
	public BasicRes delete(int quizId) throws Exception {
		if (quizId <= 0) {
			return new BasicRes(ResCodeMessage.QUIZ_ID_ERROR.getCode(), //
					ResCodeMessage.QUIZ_ID_ERROR.getMessage());
		}
		Quiz quiz = quizDao.getById(quizId);
		// 要判斷是否為 null，若不判斷且取得的值是 null 時，後續使用方法會報錯
		if (quiz == null) {
			return new BasicRes(ResCodeMessage.NOT_FOUND.getCode(), //
					ResCodeMessage.NOT_FOUND.getMessage());
		}
		// 檢查問卷狀態是否可被更新
		BasicRes checkRes = checkStatus(quiz.getStartDate(), quiz.isPublished());
		if (checkRes.getCode() != 200) { // 不等於 200 表示問卷不允許被更新
			return checkRes;
		}
		try {
//			quizDao.deleteById(quizId);
//			questionDao.deleteByQuizId(quizId);

			// ##########
			int count = quizDao.getCountByQuizId(quizId);
			if (count != 1) {
				return new BasicRes(ResCodeMessage.NOT_FOUND.getCode(), ResCodeMessage.NOT_FOUND.getMessage());
			}
			// 先刪子表，再刪主表
			questionDao.deleteByQuizId(quizId); // 通常也是 void
			quizDao.deleteById(quizId); // void，不要接回傳值
			int after = quizDao.getCountByQuizId(quizId);
			if (after != 0) {
				return new BasicRes(ResCodeMessage.QUIZ_UPDATE_FAILED.getCode(),
						ResCodeMessage.QUIZ_DELETE_FAILED.getMessage());
			}

			return new BasicRes(ResCodeMessage.SUCCESS.getCode(), ResCodeMessage.SUCCESS.getMessage());
			// ##########
		} catch (Exception e) {
			// 不能 return BasicReq 而是要將發生的異常拋出去，這樣 @Transaction 才會生效
			throw e;
		}
	}

//	@Override
//	@Transactional(rollbackFor = Exception.class)
//	public BasicRes deleteCheck(List<Integer> quizIds) throws Exception {
//		// 1) 基本檢查
//		if (quizIds == null || quizIds.isEmpty()) {
//			return new BasicRes(ResCodeMessage.QUIZ_ID_ERROR.getCode(), "Quiz Ids empty");
//		}
//		for (Integer id : quizIds) {
//			if (id == null || id <= 0) {
//				return new BasicRes(ResCodeMessage.QUIZ_ID_ERROR.getCode(), "Illegal quizId: " + id);
//			}
//			int count = quizDao.getCountByQuizId(id);
//			if (count != 1) {
//				return new BasicRes(ResCodeMessage.NOT_FOUND.getCode(), "QuizId " + id + " Not Found!!");
//			}
//		}
//		// 2) 全部都存在才開始刪（先刪子表再刪主表）
//		for (Integer id : quizIds) {
//			questionDao.deleteByQuizId(id);
//			quizDao.deleteById(id);
//		}
//		// 3) 成功
//		return new BasicRes(ResCodeMessage.SUCCESS.getCode(), ResCodeMessage.SUCCESS.getMessage());
//	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public BasicRes deleteCheck(List<Integer> quizIds) throws Exception {
		// 1) 基本檢查
		if (quizIds == null || quizIds.isEmpty()) {
			return new BasicRes(ResCodeMessage.QUIZ_ID_ERROR.getCode(), "Quiz Ids empty");
		}

		LocalDate today = LocalDate.now();
		List<Integer> notFound = new ArrayList<>();
		List<Integer> locked = new ArrayList<>(); // 已開始/進行中（或今天開始）的問卷

		// 2) 逐筆驗證可否刪除
		for (Integer id : quizIds) {
			if (id == null || id <= 0) {
				return new BasicRes(ResCodeMessage.QUIZ_ID_ERROR.getCode(), "Illegal quizId: " + id);
			}
			// 撈資料（你有自訂 native 的 getById；若改成 Optional 可用 findById）
			Quiz quiz = quizDao.getById(id);
			if (quiz == null) {
				notFound.add(id);
				continue;
			}
			LocalDate s = quiz.getStartDate();
			LocalDate e = quiz.getEndDate();

			// 進行中才禁止刪除；已結束 (e < today) 就允許刪
			boolean ongoing = quiz.isPublished() && s != null && !s.isAfter(today) // s <= today
					&& (e == null || !e.isBefore(today)); // today <= e（若 e 可能為 null，就視為尚未結束）

			if (ongoing) {
				locked.add(id);
			}

			// 若你已有 checkStatus(startDate, published) 就用它也可以：
			// BasicRes check = checkStatus(quiz.getStartDate(), quiz.isPublished());
			// if (check.getCode() != 200) locked.add(id);
		}

		// 有任何一筆不存在 → 回錯（維持原子性：全部不刪）
		if (!notFound.isEmpty()) {
			return new BasicRes(ResCodeMessage.NOT_FOUND.getCode(), "QuizId Not Found: " + notFound);
		}
		// 有任何一筆是進行中 → 回錯（維持原子性：全部不刪）
		if (!locked.isEmpty()) {
			return new BasicRes(ResCodeMessage.QUIZ_UPDATE_FAILED.getCode(), "您選擇的問卷 " + locked.stream()//
					.map(String::valueOf).collect(Collectors.joining(",")) + " 正在進行中，目前無法刪除！");
		}
		// 3) 全部都允許才開始刪（先刪子表再刪主表）
		for (Integer id : quizIds) {
			questionDao.deleteByQuizId(id);
			quizDao.deleteById(id);
		}
		// 4) 成功
		return new BasicRes(ResCodeMessage.SUCCESS.getCode(), ResCodeMessage.SUCCESS.getMessage());
	}

//	@Override
//	public SearchRes getQuizById(int quizId) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public SearchRes getQuizById(int quizId) throws Exception {

		Quiz quiz = quizDao.getById(quizId);
		if (quiz == null) {
			return new SearchRes(404, "no info");
		}
		List<QuestionVo> questionVoList = new ArrayList<>();
		// DB 取出的
		List<Question> ques = questionDao.getQuestionsByQuizId(quizId);

		for (Question item : ques) {
			String option = item.getOptions();
			try {
				List<String> optionList = mapper.readValue(option, new TypeReference<>() {
				});
				QuestionVo quizInfo = new QuestionVo(item.getQuizId(), item.getQuestionId(), //
						item.getQuestion(), item.getType(), item.isRequired(), optionList);
//				Quiz quizData = new Quiz(item.getName());
			} catch (Exception e) {
				throw e;
			}
		}

		return new SearchRes(200, "success", quiz, ques);
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public BasicRes fillin(FillinReq req) throws Exception {
		// 檢查填寫的問卷
		int count = quizDao.selectCountById(req.getQuizId(), LocalDate.now());
		if (count != 1) {
			return new BasicRes(ResCodeMessage.QUIZ_ID_ERROR.getCode(), //
					ResCodeMessage.QUIZ_ID_ERROR.getMessage());
		}
		// 檢查一個 email 只能寫同一份問卷
		count = fillinDao.selectCountByQuizIdAndEmail(req.getQuizId(), req.getEmail());
		if(count != 0) {
			return new BasicRes(ResCodeMessage.EMAIL_DUPLICATED.getCode(), //
					ResCodeMessage.EMAIL_DUPLICATED.getMessage());
		}
		// 檢查題目
		// 檢查 1. 必填但沒有答案 2. 單選但有多個答案 3. 答案跟選項一樣(答案必須是選項之一)
		// 取得一張問卷的所有題目
		List<Question> questionList = questionDao.getQuestionsByQuizId(req.getQuizId());
		List<QuestionIdAnswerVo> questionAnswerVoList = req.getQuestionAnswerVoList();
		// 將問題編號和回答轉換成 Map，就是將 QuestionAnswerVo 裡面的兩個屬性轉成 Map
		Map<Integer, List<String>> answerMap = new HashMap<>();
		for(QuestionIdAnswerVo vo : questionAnswerVoList) {
			answerMap.put(vo.getQuestionId(), vo.getAnswerList());
		}
//		// 檢查每一題
//		for(Question question : questionList) {
//			int questionId = question.getQuestionId();
//			String type = question.getType();
//			boolean required = question.isRequired();
//			// 1. 檢查必填但沒有答案 --> 必填但 questionId 沒有在 answerMap 的 key 裡面
//			if(required && !answerMap.containsKey(questionId)) {
//				return new BasicRes(ResCodeMessage.ANSWER_REQUIRED.getCode(), //
//						ResCodeMessage.ANSWER_REQUIRED.getMessage());
//			}
//			// 2. 檢查單選但有多個答案
//			if(type.equalsIgnoreCase(QuestionType.SINGLE.getType())) {
//				List<String> answerList = answerMap.get(questionId);
//				// 將每個答案比對是否被包含在選項字串中
//				if(answerList.size() > 1) {
//					return new BasicRes(ResCodeMessage.QUESTION_TYPE_IS_SINGLE.getCode(), //
//							ResCodeMessage.QUESTION_TYPE_IS_SINGLE.getMessage());
//				}
//			}
//			// 簡答題沒有選項，跳過
//			if(type.equalsIgnoreCase(QuestionType.TEXT.getType())) {
//				continue;
//			}
//			// 3. 比對該題的答案跟選項是否一樣(答案必須是選項之一)
//			String optionsStr = question.getOptions();
//			List<String> answerList = answerMap.get(questionId);
//			for(String answer : answerList) {
//				if(optionsStr.contains(answer)) {
//					return new BasicRes(ResCodeMessage.OPTION_ANSWER_MISSMATCH.getCode(), //
//							ResCodeMessage.OPTION_ANSWER_MISSMATCH.getMessage());
//				}
//			}
//		}
		// 檢查每一題
		for (Question question : questionList) {
		    int questionId = question.getQuestionId();
		    String type = question.getType();
		    boolean required = question.isRequired();

		    List<String> answerList = answerMap.get(questionId);

		    // 1) 必填沒答
		    if (required && (answerList == null || answerList.isEmpty())) {
		        return new BasicRes(ResCodeMessage.ANSWER_REQUIRED.getCode(),
		                ResCodeMessage.ANSWER_REQUIRED.getMessage());
		    }

		    // 2) 單選多答
		    if (type.equalsIgnoreCase(QuestionType.SINGLE.getType())) {
		        if (answerList != null && answerList.size() > 1) {
		            return new BasicRes(ResCodeMessage.QUESTION_TYPE_IS_SINGLE.getCode(),
		                    ResCodeMessage.QUESTION_TYPE_IS_SINGLE.getMessage());
		        }
		    }

		    // 3) 簡答題不做選項比對
		    if (type.equalsIgnoreCase(QuestionType.TEXT.getType())) {
		        continue;
		    }

		    // 4) 選項比對（把 DB 的 JSON 轉回 List<String>）
		    List<String> optionList;
		    try {
		        optionList = mapper.readValue(question.getOptions(),
		                new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {});
		    } catch (Exception e) {
		        return new BasicRes(ResCodeMessage.OPTIONS_TRANSFER_ERROR.getCode(),
		                ResCodeMessage.OPTIONS_TRANSFER_ERROR.getMessage());
		    }

		    // （可選）輕量正規化，避免全/半形空白等微差
		    java.util.function.Function<String,String> normalize = s ->
		        s == null ? "" : s.trim().replace("\u00A0"," ").replaceAll("\\s+"," ");

		    java.util.Set<String> allowed = optionList.stream()
		            .map(normalize)
		            .collect(java.util.stream.Collectors.toSet());

		    if (answerList != null) {
		        boolean allValid = answerList.stream()
		                .map(normalize)
		                .allMatch(allowed::contains);

		        if (!allValid) {
		            return new BasicRes(ResCodeMessage.OPTION_ANSWER_MISSMATCH.getCode(),
		                    ResCodeMessage.OPTION_ANSWER_MISSMATCH.getMessage());
		        }
		    }
		}

		// 存資料：一題存成一筆資料
		for(QuestionIdAnswerVo vo : questionAnswerVoList) {
			// 把 answerList 轉換成字串型態
			try {
				String str = mapper.writeValueAsString(vo.getAnswerList());
				fillinDao.insert(req.getQuizId(), vo.getQuestionId(), req.getEmail(), //
						str, LocalDate.now());
			} catch (Exception e) {
				throw e;
			}
		}
		return new BasicRes(ResCodeMessage.SUCCESS.getCode(), //
				ResCodeMessage.SUCCESS.getMessage());
	}

	public BasicRes fillin_test(FillinReq req) {
		// 檢查填寫的問卷
		int count = quizDao.selectCountById(req.getQuizId(), LocalDate.now());
		if (count != 1) {
			return new BasicRes(ResCodeMessage.QUIZ_ID_ERROR.getCode(), //
					ResCodeMessage.QUIZ_ID_ERROR.getMessage());
		}
		// 檢查題目
		// 檢查 1. 必填但沒有答案 2. 單選但有多個答案 3. 答案跟選項一樣(答案必須是選項之一)
		// 取得一張問卷的所有題目
		List<Question> questionList = questionDao.getQuestionsByQuizId(req.getQuizId());
		List<QuestionIdAnswerVo> questionAnswerVoList = req.getQuestionAnswerVoList();

		// 先把必填題的 questionId 放到一個 List 中
		List<Integer> questionIdList = new ArrayList<>();
		for (Question question : questionList) {
			if (question.isRequired()) {
				questionIdList.add(question.getQuestionId());
			}
		}
		for (Question question : questionList) {
			int questionId = question.getQuestionId();
			String type = question.getType();
			boolean required = question.isRequired();
			// 該提是必填 --> 檢查 VoList 中是否有該題的編號存在
			for (QuestionIdAnswerVo vo : questionAnswerVoList) {
				int voQuestionId = vo.getQuestionId();
				// 該題必填但題目編號不包含在 questionIdList，回傳錯誤
				if (required && !questionIdList.contains(questionIdList)) {
					return new BasicRes(ResCodeMessage.ANSWER_REQUIRED.getCode(), //
							ResCodeMessage.ANSWER_REQUIRED.getMessage());
				}
				List<String> answerList = vo.getAnswerList();
				// 檢查相同的 questionId，該題是必填但沒有答案 --> 回傳錯誤
				if (questionId == voQuestionId && required//
				// CollectionUtils.isEmpty 有判斷到 null
				// QuestionAnswerVo 中的 answerList 有給定新的預設值，沒有 Mapping 到時，會是一個空的 List
						&& answerList.isEmpty()) {
					return new BasicRes(ResCodeMessage.ANSWER_REQUIRED.getCode(), //
							ResCodeMessage.ANSWER_REQUIRED.getMessage());
				}
				// 檢查相同的 questionId，單選但有多個答案 --> 回傳錯誤
				if (questionId == voQuestionId && type == QuestionType.SINGLE.getType() //
						&& answerList.size() > 1) {
					return new BasicRes(ResCodeMessage.QUESTION_TYPE_IS_SINGLE.getCode(), //
							ResCodeMessage.QUESTION_TYPE_IS_SINGLE.getMessage());

				}
			}
		}
		return null;
	}
	
	
	@Override
	public FeedbackUserRes feedbackUserList(int quizId) {
		if(quizId <= 0) {
			return new FeedbackUserRes(ResCodeMessage.QUIZ_ID_ERROR.getCode(), //
					ResCodeMessage.QUIZ_ID_ERROR.getMessage());
		}
		List<UserVo> userVoList = fillinDao.selectUserVoList(quizId);
		
		return new FeedbackUserRes(ResCodeMessage.SUCCESS.getCode(), //
					ResCodeMessage.SUCCESS.getMessage(), quizId, userVoList);
	}

	@Override
	public FeedbackRes feedback(int quizId, String email) {
		if(quizId <= 0) {
			return new FeedbackRes(ResCodeMessage.QUIZ_ID_ERROR.getCode(), //
					ResCodeMessage.QUIZ_ID_ERROR.getMessage());
		}
		List<QuestionAnswerDto> dtoList = fillinDao.selectQuestionAnswerList(quizId, email);
		List<QuestionAnswerVo> voList = new ArrayList<>();
		for(QuestionAnswerDto dto : dtoList) {
			try {
				List<String> answerList = mapper.readValue(dto.getAnswerStr(), new TypeReference<>(){
				});
				QuestionAnswerVo vo = new QuestionAnswerVo(dto.getQuestionId(), //
						dto.getQuestion(), dto.getType(), dto.isRequired(), answerList);
				voList.add(vo);
			} catch (Exception e) {
				// 不需要 throw
				return new FeedbackRes(ResCodeMessage.OBJECT_MAPPER_PROCESSING_ERROR.getCode(), //
						ResCodeMessage.OBJECT_MAPPER_PROCESSING_ERROR.getMessage());
			}
			
		}
		return new FeedbackRes(ResCodeMessage.SUCCESS.getCode(), //
				ResCodeMessage.SUCCESS.getMessage(), voList);
	}
	
	@Override
	public StatisticsRes statistics(int quizId) {
	    if (quizId <= 0) {
	        return new StatisticsRes(ResCodeMessage.QUIZ_ID_ERROR.getCode(),
	                                 ResCodeMessage.QUIZ_ID_ERROR.getMessage());
	    }

	    // 1) 彙總所有（非 TEXT）題目的作答
	    List<QuestionAnswerDto> dtoList = fillinDao.selectQuestionAnswerList(quizId);
	    Map<Integer, List<String>> answersByQid = new HashMap<>();

	    for (QuestionAnswerDto dto : dtoList) {
	        if (QuestionType.TEXT.getType().equalsIgnoreCase(dto.getType())) {
	            continue;
	        }
	        try {
	            List<String> answerList = mapper.readValue(
	                dto.getAnswerStr(), new TypeReference<List<String>>() {}
	            );
	            // 正確使用 key
	            answersByQid.computeIfAbsent(dto.getQuestionId(), k -> new ArrayList<>())
	                        .addAll(answerList);
	        } catch (Exception e) {
	            return new StatisticsRes(ResCodeMessage.OBJECT_MAPPER_PROCESSING_ERROR.getCode(),
	                                     ResCodeMessage.OBJECT_MAPPER_PROCESSING_ERROR.getMessage());
	        }
	    }

	    // 2) 準備每題的選項清單（非 TEXT）
	    List<Question> questionList = questionDao.getQuestionsByQuizId(quizId);
	    Map<Integer, List<OptionCountVo>> optionVoMap = new HashMap<>();

	    for (Question q : questionList) {
	        if (QuestionType.TEXT.getType().equalsIgnoreCase(q.getType())) continue;

	        try {
	            List<String> optionList = mapper.readValue(
	                q.getOptions(), new TypeReference<List<String>>() {}
	            );
	            List<OptionCountVo> voList = optionList.stream()
	                .map(op -> new OptionCountVo(op, 0))
	                .collect(Collectors.toList());
	            optionVoMap.put(q.getQuestionId(), voList);
	        } catch (Exception e) {
	            return new StatisticsRes(ResCodeMessage.OBJECT_MAPPER_PROCESSING_ERROR.getCode(),
	                                     ResCodeMessage.OBJECT_MAPPER_PROCESSING_ERROR.getMessage());
	        }
	    }

	    // 3) 計數（不要改動原答案清單）
	    for (Map.Entry<Integer, List<OptionCountVo>> entry : optionVoMap.entrySet()) {
	        int questionId = entry.getKey();
	        List<OptionCountVo> voList = entry.getValue();

	        List<String> answers = answersByQid.getOrDefault(questionId, Collections.emptyList());
	        // 也可用 groupingBy 先做出 frequency map
	        Map<String, Long> freq = answers.stream()
	            .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

	        for (OptionCountVo vo : voList) {
	            vo.setCount(freq.getOrDefault(vo.getOption(), 0L).intValue());
	        }
	    }

	    // 4) 組 StatisticsVo（按照 dtoList 或 questionList 關聯）
	    List<StatisticsVo> statisticsVoList = new ArrayList<>();
	    // 用 questionList 來產出（每題只建一次）
	    Map<Integer, Question> qById = questionList.stream()
	        .collect(Collectors.toMap(Question::getQuestionId, q -> q, (a,b)->a));

	    for (Map.Entry<Integer, List<OptionCountVo>> entry : optionVoMap.entrySet()) {
	        int qid = entry.getKey();
	        Question q = qById.get(qid);
	        if (q == null) continue;

	        statisticsVoList.add(new StatisticsVo(
	            qid,
	            q.getQuestion(),
	            q.getType(),
	            q.isRequired(),
	            entry.getValue()
	        ));
	    }

	    return new StatisticsRes(ResCodeMessage.SUCCESS.getCode(),
	                             ResCodeMessage.SUCCESS.getMessage(),
	                             statisticsVoList);
	}


//	@Override
//	public StatisticsRes statistics(int quizId) {
//		if(quizId <= 0) {
//			return new StatisticsRes(ResCodeMessage.QUIZ_ID_ERROR.getCode(), //
//					ResCodeMessage.QUIZ_ID_ERROR.getMessage());
//		}
//		List<QuestionAnswerDto> dtoList = fillinDao.selectQuestionAnswerList(quizId);
//		Map<Integer, List<String>> quIdAnswerMap = new HashMap<>();
//		for(QuestionAnswerDto dto : dtoList) {
//			if(dto.getType().equalsIgnoreCase(QuestionType.TEXT.getType())) {
//				continue;
//			}
//			try {
//				List<String> answerList = mapper.readValue(dto.getAnswerStr(), new TypeReference<>(){
//				});
//				if(quIdAnswerMap.containsKey(dto.getQuestionId())) {
//					List<String> oldList = quIdAnswerMap.get(quIdAnswerMap);
//					oldList.addAll(answerList);
//					quIdAnswerMap.put(dto.getQuestionId(), oldList);
//				} else {
//					quIdAnswerMap.put(dto.getQuestionId(), answerList);
//				}
//				quIdAnswerMap.put(dto.getQuestionId(), answerList);
//
//			} catch (Exception e) {
//				// 不需要 throw
//				return new StatisticsRes(ResCodeMessage.OBJECT_MAPPER_PROCESSING_ERROR.getCode(), //
//						ResCodeMessage.OBJECT_MAPPER_PROCESSING_ERROR.getMessage());
//			}
//		}
//		List<Question> questionList = questionDao.getQuestionsByQuizId(quizId);
//		Map<Integer, List<OptionCountVo>> quIdOptionVoListMap = new HashMap<>();
//		for(Question question : questionList) {
//			try {
//				if(question.getType().equalsIgnoreCase(QuestionType.TEXT.getType())) {
//					continue;
//				}
//				List<String> optionList = mapper.readValue(question.getOptions(), new TypeReference<>(){
//				});
//				List<OptionCountVo> voList = new ArrayList<>();
//				for(String op : optionList) {
//					OptionCountVo vo = new OptionCountVo(op, 0);
//					voList.add(vo);
//				}
//				quIdOptionVoListMap.put(question.getQuestionId(), voList);
//			} catch (Exception e) {
//				return new StatisticsRes(ResCodeMessage.OBJECT_MAPPER_PROCESSING_ERROR.getCode(), //
//						ResCodeMessage.OBJECT_MAPPER_PROCESSING_ERROR.getMessage());
//			}
//		}
//		for(Entry<Integer, List<OptionCountVo>> map : quIdOptionVoListMap.entrySet()) {
//			int questionId = map.getKey();
//			List<OptionCountVo> voList = map.getValue();
//			List<String> answerList = quIdAnswerMap.get(questionId);
//			
//			for(OptionCountVo vo : voList) {
//				int size = answerList.size();
//				String option = vo.getOption();
//				answerList.removeAll(List.of(option));
//				int newSize = answerList.size();
//				int count = size - newSize;
//				vo.setCount(count);		
//			}
//		}
//		List<StatisticsVo> statisticsVoList = new ArrayList<>();
//		for(Entry<Integer, List<OptionCountVo>> map : quIdOptionVoListMap.entrySet()) {
//			for(QuestionAnswerDto dto : dtoList) {
//				if(map.getKey() == dto.getQuestionId()) {
//					StatisticsVo vo = new StatisticsVo(dto.getQuestionId(), dto.getQuestion(), //
//							dto.getType(), dto.isRequired(), map.getValue());
//					statisticsVoList.add(vo);
//				}
//			}
//		}
//		return new StatisticsRes(ResCodeMessage.SUCCESS.getCode(), //
//				ResCodeMessage.SUCCESS.getMessage(), statisticsVoList);
//	}

	

}
