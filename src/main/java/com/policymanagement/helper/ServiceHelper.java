package com.policymanagement.helper;

import org.springframework.stereotype.Service;

import com.policymanagement.enumuration.ResultCode;
import com.policymanagement.exceptionHandler.PolicyManagementException;

@Service
public class ServiceHelper {
	
	public String extractToken(String authHeader) {
	    if (authHeader != null && authHeader.startsWith("Bearer ")) {
	        return authHeader.substring(7);
	    }
	    throw new PolicyManagementException(ResultCode.UNAUTHORIZED); 
	}

}
