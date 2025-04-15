package com.policymanagement.exceptionHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import com.policymanagement.enumuration.ResultCode;
import com.policymanagement.responseDto.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;
    private HttpServletRequest mockRequest;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
        mockRequest = mock(HttpServletRequest.class);
    }

    @Test
    void testHandleMissingFile() {
        MissingServletRequestPartException exception = new MissingServletRequestPartException("file");
        ResponseEntity<ApiResponse> response = handler.handleMissingFile(exception, mockRequest);

        assertEquals(400, response.getStatusCode().value());
        assertEquals(ResultCode.FILE_MISSING.getMessage(), response.getBody().getMessage());
    }


    @Test
    void testHandleMissingParam() {
        MissingServletRequestParameterException exception =
                new MissingServletRequestParameterException("author", "String");

        ResponseEntity<ApiResponse> response = handler.handleMissingParam(exception, mockRequest);

        assertEquals(400, response.getStatusCode().value());
        assertEquals(ResultCode.BAD_REQUEST.getMessage(), response.getBody().getMessage());
    }


    @Test
    void testHandleMaxSizeExceeded() {
        MaxUploadSizeExceededException exception = new MaxUploadSizeExceededException(10 * 1024 * 1024);

        ResponseEntity<ApiResponse> response = handler.handleMaxSizeException(exception, mockRequest);

        assertEquals(400, response.getStatusCode().value());
        assertEquals(ResultCode.FILE_SIZE_EXCEEDED.getMessage(), response.getBody().getMessage());
    }


    @Test
    void testHandleGenericException() {
        Exception exception = new Exception("Unexpected error");

        ResponseEntity<ApiResponse> response = handler.handleGenericException(exception, mockRequest);

        assertEquals(400, response.getStatusCode().value());
        assertEquals(ResultCode.FAILED.getMessage(), response.getBody().getMessage());
    }

    
    
}
