package com.girlfriend.sunghun.domain.auth.presentation;

import com.girlfriend.sunghun.domain.auth.presentation.dto.request.SignInRequest;
import com.girlfriend.sunghun.domain.auth.presentation.dto.request.SignUpRequest;
import com.girlfriend.sunghun.domain.auth.presentation.dto.response.UserRankResponse;
import com.girlfriend.sunghun.domain.auth.presentation.dto.response.UserTokenResponse;
import com.girlfriend.sunghun.domain.auth.service.AuthService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @ApiOperation("갓생 살 사람 계정 회원가입")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    public void signUp(
            @RequestBody SignUpRequest request
    ) {
        authService.signUp(request);
    }

    @ApiOperation("갓생 회원 로그인")
    @PostMapping("/sign-in")
    public UserTokenResponse signIn(
            @RequestBody SignInRequest request
    ) {
        return authService.signIn(request);
    }

    @ApiOperation("유저 랭킹 불러오기")
    @GetMapping("/ranking")
    public UserRankResponse getUserRank() {
        return authService.getUserRank();
    }
}
