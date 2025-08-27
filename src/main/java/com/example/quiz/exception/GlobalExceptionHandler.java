package com.example.quiz.exception;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 1. @RestControllerAdvice 包含了 @ControllerAdvice 和 @ResponseBody <br>
 * 1.1. @ControllerAdvice: 使用於 class 的註釋，用來定義「全域」的例外處理策略<br>
 * 1.2. @ResponseBody: 這是一個告訴 Spring 每次 response 都需要將返回的物件序列化為 JSON 或 XML 格式，
 *                   然後放入 HTTP 回應的內容中<br>
 * 2. @ExceptionHandler: 使用於 method 中的註釋，提供了在 controller 處理例外的功能<br>
 * 3. ResponseEntity: 可以做為 controller 的返回值
 */
@RestControllerAdvice //用來表示這是一個全域的 REST 例外處理器
public class GlobalExceptionHandler {

	// @Valid 配合 Spring 會抛出 MethodArgumentNotValidException 異常；所以要針對此 Exception 處理
	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ResponseEntity<Map<String, Object>> paramExceptionHandler(MethodArgumentNotValidException e) {
		// 因為在 controller 中的 res 返回的是 code: 數值 和 message: 字串；所以 map 的 value 用 Object 接
		Map<String, Object> errorMap = new HashMap<>();
		// 將 req 中的參數檢查統一歸類為 bad_request，代碼為 400
		errorMap.put("code", HttpStatus.BAD_REQUEST.value());
		// 即使有多個錯誤參數，但還是一個個處理，所以會取第一個錯誤訊息
		errorMap.put("message", e.getAllErrors().get(0).getDefaultMessage());
		// 返回的 Http Status Code 為 badRequest(400)
		return ResponseEntity.badRequest().body(errorMap);
	}
	
	@ExceptionHandler({SQLException.class})
	public ResponseEntity<Map<String, Object>> handleSQLException(SQLException e) {// 抓取 SQL Exception
		// 因為在 controller 中不符合資料驗證規則時，res 返回的是 code: 數值 和 message: 字串；所以 map 的 value 用 Object 接
		Map<String, Object> errorMap = new HashMap<>();
		// 將 SQL 發生的 Exception 歸類為 internal_server_error，代碼為 500
		errorMap.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
		// 即使有多個錯誤參數，但還是一個個處理，所以會取第一個錯誤訊息
		errorMap.put("message", e.getMessage());
		return ResponseEntity.internalServerError().body(errorMap);
	}
	
	@ExceptionHandler({Exception.class})
	public ResponseEntity<Map<String, Object>> handleException(Exception e) {// 抓取 Exception
		// 因為在 controller 中不符合資料驗證規則時，res 返回的是 code: 數值 和 message: 字串；所以 map 的 value 用 Object 接
		Map<String, Object> errorMap = new HashMap<>();
		// 將 SQL 發生的 Exception 歸類為 internal_server_error，代碼為 500
		errorMap.put("code", HttpStatus.BAD_REQUEST.value());
		// 即使有多個錯誤參數，但還是一個個處理，所以會取第一個錯誤訊息
		errorMap.put("message", e.getMessage());
		return ResponseEntity.internalServerError().body(errorMap);
	}
	
}