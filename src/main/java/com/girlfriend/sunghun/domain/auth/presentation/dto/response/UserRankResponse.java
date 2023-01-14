package com.girlfriend.sunghun.domain.auth.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter @AllArgsConstructor
@Builder
public class UserRankResponse {

    private List<UserResponse> rank;

}
