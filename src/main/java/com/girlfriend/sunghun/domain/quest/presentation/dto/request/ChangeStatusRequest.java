package com.girlfriend.sunghun.domain.quest.presentation.dto.request;

import com.girlfriend.sunghun.domain.quest.domain.type.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class ChangeStatusRequest {

    private Status status;
    private Long hour;

}
