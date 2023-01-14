package com.girlfriend.sunghun.domain.auth.presentation.dto.request;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class SignInRequest {

    @ApiParam(value = "유저 아이디", required = true)
    private String userId;

    @ApiParam(value = "유저 비밀번호", required = true)
    private String userPw;

}
