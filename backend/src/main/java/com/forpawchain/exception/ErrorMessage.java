package com.forpawchain.exception;

import org.springframework.http.HttpStatus;

public enum ErrorMessage {
	VALIDATION_FAIL_EXCEPTION(-1, "입력 값의 조건이 잘못 되었습니다.", HttpStatus.BAD_REQUEST),
	// UNDEFINED_EXCEPTION(0, "정의되지 않은 에러입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
	// BINDING_FAIL_EXCEPTION(1, "내부 서버에서 오류가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
	//
	NOT_PERMISSION_EXCEPTION(5, "권한이 없거나 부족합니다.", HttpStatus.FORBIDDEN),
	//
	// REQUEST_EXCEPTION(10, "", HttpStatus.BAD_REQUEST),
	NOT_EXIST_CONTENT(20, "존재하지 않는 컨텐츠입니다.", HttpStatus.BAD_REQUEST),
	EXIST_CONTENT(21, "이미 존재하는 컨텐츠입니다.", HttpStatus.BAD_REQUEST),
	// NOT_EXIST_ID(100, "서버에 존재하지 않는 아이디입니다.", HttpStatus.BAD_REQUEST),
	// NOT_PASSWORD(101, "잘못된 비밀번호입니다.", HttpStatus.BAD_REQUEST),
	// DONT_EXIST_ACCOUNT(102, "이미 삭제 된 계정입니다.", HttpStatus.BAD_REQUEST),
	// NOT_MATCH_ACCOUNT_INFO(103, "입력하신 계정과 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
	// SIGNUP_LISTEN(110, "가입 대기중인 아이디입니다.", HttpStatus.BAD_REQUEST),
	//
	//
	// EXIST_NICKNAME(200, "이미 존재하는 닉네임입니다.", HttpStatus.BAD_REQUEST),
	// EXIST_EMAIL(201, "이미 가입된 이메일입니다.", HttpStatus.BAD_REQUEST),
	// EXIST_ID(202, "이미 가입된 아이디입니다.", HttpStatus.BAD_REQUEST),
	//
	// NOT_USER_INFO_MATCH(210, "유저 정보가 옳바르지 않습니다.", HttpStatus.BAD_REQUEST),
	//
	//
	// EXIST_CHECK_MAIL(250, "이미 인증된 이메일입니다.", HttpStatus.BAD_REQUEST),
	EXIST_WALLET(251, "이미 의사 면허 인증이 완료되었습니다.", HttpStatus.BAD_REQUEST),
	//
	// NOT_TIMESTAMP(205, "탈퇴 후 재 가입은 24시간이 넘어서 가능합니다.", HttpStatus.BAD_REQUEST),
	NOT_USER_INFO(300, "유저정보가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
	//
	// REFRESH_TOKEN_EXPIRE(900, "리프세리 토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
	// REFRESH_TOKEN_NOT_MATCH(901, "리프레시 토큰이 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),
	//
	// ACCESS_TOKEN_EXPIRE(1000, "토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
	// ACCESS_TOKEN_INVALID(1001, "토큰이 잘못되었습니다.", HttpStatus.UNAUTHORIZED),
	// ACCESS_TOKEN_NOT_LOAD(1002, "토큰을 불러오지 못하였습니다.", HttpStatus.UNAUTHORIZED),
	// ACCESS_TOKEN_INVALID_STRUCT(1010, "토큰이 구조가 잘못되었습니다.", HttpStatus.UNAUTHORIZED),
	// ACCESS_TOKEN_INVALID_HEADER(1011, "토큰 해더가 손상되었습니다.", HttpStatus.UNAUTHORIZED),
	// ACCESS_TOKEN_INVALID_PAYLOADS(1012, "토큰 정보가 손상되었습니다.", HttpStatus.UNAUTHORIZED),
	// ACCESS_TOKEN_INVALID_SIGNATURE(1013, "토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED),
	// ACCESS_TOKEN_EMPTY(1014, "토큰이 입력되지 않았습니다.", HttpStatus.BAD_REQUEST);
	private final Integer code;
	private final String errMsg;
	private final HttpStatus httpStatus;

	ErrorMessage(int code, String errMsg, HttpStatus httpStatus) {
		this.code = code;
		this.errMsg = errMsg;
		this.httpStatus = httpStatus;
	}

	public int getErrorCode() {
		return code;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public String getErrorMessage() {
		return errMsg;
	}
}
