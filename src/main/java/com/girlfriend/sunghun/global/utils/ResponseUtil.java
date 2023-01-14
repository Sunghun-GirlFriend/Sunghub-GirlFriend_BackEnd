package com.girlfriend.sunghun.global.utils;

import com.girlfriend.sunghun.domain.auth.domain.User;
import com.girlfriend.sunghun.domain.auth.presentation.dto.response.UserResponse;
import com.girlfriend.sunghun.domain.quest.domain.Quest;
import com.girlfriend.sunghun.domain.quest.presentation.dto.response.QuestResponse;
import com.girlfriend.sunghun.domain.quest.presentation.dto.response.QuestWaitResponse;

public class ResponseUtil {

    public static UserResponse getUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId()).userId(user.getUserId())
                .userPw(user.getUserPw()).name(user.getName())
                .profileImage(user.getProfileImage()).description(user.getDescription())
                .point(user.getPoint()).build();
    }

    public static QuestResponse getQuestResponse(Quest quest) {
        return QuestResponse.builder()
                .questId(quest.getQuestId()).questTitle(quest.getQuestTitle())
                .category(quest.getCategory()).type(quest.getType()).status(quest.getStatus())
                .userData(getUserResponse(quest.getUser()))
                .build();
    }

}
