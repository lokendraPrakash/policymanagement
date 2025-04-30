package com.policymanagement.service;

import com.policymanagement.requestDto.LoginRequestDto;
import com.policymanagement.requestDto.RegisterRequestDto;
import com.policymanagement.responseDto.LoginResponseDto;

public interface AuthService {
    LoginResponseDto login(LoginRequestDto loginRequestDto);
    void register(RegisterRequestDto registerRequestDto);
}