package com.girlfriend.sunghun.domain.quest.presentation.dto.response;

import com.girlfriend.sunghun.domain.quest.domain.type.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter @AllArgsConstructor
@Builder
public class QuestListResponse {

    private List<QuestResponse> runningList;

    private List<QuestResponse> beforeList;

    private List<QuestResponse> completeList;

    private List<QuestWaitResponse> waitList;

    private List<QuestResponse> terminateList;

}
