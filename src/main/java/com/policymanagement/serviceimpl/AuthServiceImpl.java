package com.policymanagement.serviceimpl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.policymanagement.config.JwtUtil;
import com.policymanagement.entity.UserEntity;
import com.policymanagement.repository.UserRepository;
import com.policymanagement.requestDto.LoginRequestDto;
import com.policymanagement.requestDto.RegisterRequestDto;
import com.policymanagement.responseDto.LoginResponseDto;
import com.policymanagement.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;

	public AuthServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public void register(RegisterRequestDto registerRequestDto) {
		UserEntity user = UserEntity.builder().username(registerRequestDto.getUsername())
				.password(passwordEncoder.encode(registerRequestDto.getPassword())).build();
		userRepository.save(user);
	}

	@Override
	public LoginResponseDto login(LoginRequestDto loginRequestDto) {
		UserEntity user = userRepository.findByUsername(loginRequestDto.getUsername())
				.orElseThrow(() -> new RuntimeException("Invalid username or password"));

		if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
			throw new RuntimeException("Invalid username or password");
		}

		String token = JwtUtil.generateToken(user.getUsername());
		String refreshToken = JwtUtil.generateRefreshToken(user.getUsername());

		return LoginResponseDto.builder().accessToken(token).refreshToken(refreshToken).build();
	}
}
