package com.girlfriend.sunghun.domain.auth.service;

import com.girlfriend.sunghun.domain.auth.domain.User;
import com.girlfriend.sunghun.domain.auth.domain.repository.UserRepository;
import com.girlfriend.sunghun.domain.auth.exception.PasswordWrongException;
import com.girlfriend.sunghun.domain.auth.exception.UserAlreadyExistsByIdException;
import com.girlfriend.sunghun.domain.auth.exception.UserNotFoundException;
import com.girlfriend.sunghun.domain.auth.presentation.dto.request.SignInRequest;
import com.girlfriend.sunghun.domain.auth.presentation.dto.request.SignUpRequest;
import com.girlfriend.sunghun.domain.auth.presentation.dto.response.UserRankResponse;
import com.girlfriend.sunghun.domain.auth.presentation.dto.response.UserResponse;
import com.girlfriend.sunghun.domain.auth.presentation.dto.response.UserTokenResponse;
import com.girlfriend.sunghun.global.security.JwtProvider;
import com.girlfriend.sunghun.global.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public UserRankResponse getUserRank() {
        Pageable pageable = PageRequest.of(0, 50, Sort.by("point").descending());
        Page<User> userList = userRepository.findAll(pageable);

        List<UserResponse> rankList = userList.stream().map(ResponseUtil::getUserResponse).collect(Collectors.toList());

        return UserRankResponse.builder()
                .rank(rankList)
                .build();
    }

}
