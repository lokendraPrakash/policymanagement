package com.policymanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.policymanagement.requestDto.LoginRequestDto;
import com.policymanagement.requestDto.RegisterRequestDto;
import com.policymanagement.responseDto.ApiResponse;
import com.policymanagement.responseDto.LoginResponseDto;
import com.policymanagement.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

	@PostMapping("/register")
	public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequestDto requestDto) {
		authService.register(requestDto);
		return ResponseEntity
				.ok(ApiResponse.builder().code(1200).status("SUCCESS").message("User registered successfully").build());
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse> login(@RequestBody LoginRequestDto loginRequestDto) {
		LoginResponseDto response = authService.login(loginRequestDto);
		return ResponseEntity.ok(
				ApiResponse.builder().code(1200).status("SUCCESS").message("Login successful").data(response).build());
	}
}
