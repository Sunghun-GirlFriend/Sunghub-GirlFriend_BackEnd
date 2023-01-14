package com.girlfriend.sunghun.domain.auth.service;

import com.girlfriend.sunghun.domain.auth.domain.User;
import com.girlfriend.sunghun.domain.auth.domain.repository.UserRepository;
import com.girlfriend.sunghun.domain.auth.exception.PasswordWrongException;
import com.girlfriend.sunghun.domain.auth.exception.UserAlreadyExistsByIdException;
import com.girlfriend.sunghun.domain.auth.exception.UserNotFoundException;
import com.girlfriend.sunghun.domain.auth.presentation.dto.request.SignInRequest;
import com.girlfriend.sunghun.domain.auth.presentation.dto.request.SignUpRequest;
import com.girlfriend.sunghun.domain.auth.presentation.dto.response.UserTokenResponse;
import com.girlfriend.sunghun.global.security.JwtProvider;
import com.girlfriend.sunghun.global.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public void signUp(SignUpRequest request) {
        userRepository.findByUserId(request.getUserId())
                .ifPresent(m -> {
                    throw UserAlreadyExistsByIdException.EXCEPTION;
                });

        User user = User.builder()
                .userId(request.getUserId()).userPw(passwordEncoder.encode(request.getUserPw()))
                .name(request.getName()).description(request.getDescription())
                .profileImage(request.getProfileImage()).description(request.getDescription())
                .build();
        userRepository.save(user);
    }

    @Transactional
    public UserTokenResponse signIn(SignInRequest request) {
        User user = userRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        if(passwordEncoder.matches(request.getUserPw(), user.getUserPw())) {
            return UserTokenResponse.builder()
                    .accessToken(jwtProvider.generateAccessToken(user.getId()))
                    .userData(ResponseUtil.getUserResponse(user))
                    .build();
        } else {
            throw PasswordWrongException.EXCEPTION;
        }
    }

}
