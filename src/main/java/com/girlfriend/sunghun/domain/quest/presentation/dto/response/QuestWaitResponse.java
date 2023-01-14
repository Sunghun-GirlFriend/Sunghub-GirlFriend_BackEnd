package com.girlfriend.sunghun.domain.quest.presentation.dto.response;

import com.girlfriend.sunghun.domain.auth.presentation.dto.response.UserResponse;
import com.girlfriend.sunghun.domain.quest.domain.type.Category;
import com.girlfriend.sunghun.domain.quest.domain.type.QuestType;
import com.girlfriend.sunghun.domain.quest.domain.type.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter @AllArgsConstructor
@Builder
public class QuestWaitResponse {

    private Long questId;
    private String questTitle;
    private Category category;
    private UserResponse userData;
    private QuestType type;
    private Status status;
    private String time;

}
