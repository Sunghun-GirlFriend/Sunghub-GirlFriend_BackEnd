package com.girlfriend.sunghun.domain.auth.presentation.dto.request;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class SignUpRequest {

    @ApiParam(value = "유저 아이디", required = true)
    private String userId;

    @ApiParam(value = "유저 비밀번호", required = true)
    private String userPw;

    @ApiParam(value = "유저 이름", required = false)
    private String name;

    @ApiParam(value = "갓생 설명", required = false)
    private String description;

    @ApiParam(value = "유저 프로필 이미지(변경 예정)", required = false)
    private String profileImage;

}
