package com.girlfriend.sunghun.global.utils;

import com.girlfriend.sunghun.domain.quest.domain.Quest;
import com.girlfriend.sunghun.domain.quest.domain.type.Status;
import com.girlfriend.sunghun.domain.quest.presentation.dto.response.QuestResponse;

import java.util.List;
import java.util.stream.Collectors;

public class FilterUtil {

    public static List<QuestResponse> statusFilter(List<Quest> list, Status status) {
        return list.stream()
                .filter(i -> i.getStatus().equals(status))
                .map(ResponseUtil::getQuestResponse)
                .collect(Collectors.toList());
    }

}
