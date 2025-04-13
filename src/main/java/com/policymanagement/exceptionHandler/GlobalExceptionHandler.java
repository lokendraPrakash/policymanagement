package com.policymanagement.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import com.policymanagement.enumuration.ResultCode;
import com.policymanagement.responseDto.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MissingServletRequestPartException.class)
	public ResponseEntity<ApiResponse> handleMissingFile(MissingServletRequestPartException ex,
			HttpServletRequest request) {
		log.warn("Missing file part in request: {}", request.getRequestURI());
		return buildResponse(ResultCode.FILE_MISSING);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ApiResponse> handleMissingParam(MissingServletRequestParameterException ex,
			HttpServletRequest request) {
		log.warn("Missing parameter '{}' in request: {}", ex.getParameterName(), request.getRequestURI());
		return buildResponse(ResultCode.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse> handleGenericException(Exception ex, HttpServletRequest request) {
		log.error("Unexpected error at {}: {}", request.getRequestURI(), ex.getMessage(), ex);
		return buildResponse(ResultCode.FAILED);
	}

	private ResponseEntity<ApiResponse> buildResponse(ResultCode resultCode) {
		ApiResponse response = ApiResponse.builder().code(resultCode.getCode()).status(resultCode.getStatus())
				.message(resultCode.getMessage()).build();
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<ApiResponse> handleMaxSizeException(MaxUploadSizeExceededException ex, HttpServletRequest request) {
		log.warn("File size exceeded in request: {}", request.getRequestURI());
		return buildResponse(ResultCode.FILE_SIZE_EXCEEDED);
	}
}