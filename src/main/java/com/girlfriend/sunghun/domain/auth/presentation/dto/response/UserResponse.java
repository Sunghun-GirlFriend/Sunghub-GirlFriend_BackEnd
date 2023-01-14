package com.girlfriend.sunghun.domain.auth.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter @AllArgsConstructor
@Builder
public class UserResponse {

    private Long id;
    private String userId;
    private String userPw;
    private String name;
    private String profileImage;
    private String description;
    private int point;

}
