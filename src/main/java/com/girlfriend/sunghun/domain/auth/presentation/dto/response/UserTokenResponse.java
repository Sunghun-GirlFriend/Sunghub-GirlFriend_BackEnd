package com.girlfriend.sunghun.domain.auth.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter @AllArgsConstructor
@Builder
public class UserTokenResponse {

    private String accessToken;

    private UserResponse userData;

}
