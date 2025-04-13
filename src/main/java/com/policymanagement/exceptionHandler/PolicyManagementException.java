package com.policymanagement.exceptionHandler;

import com.policymanagement.enumuration.ResultCode;

import lombok.Getter;

@Getter
public class PolicyManagementException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final ResultCode resultCode;

	public PolicyManagementException(ResultCode resultCode) {
		super(resultCode.getMessage());
		this.resultCode = resultCode;
	}
}