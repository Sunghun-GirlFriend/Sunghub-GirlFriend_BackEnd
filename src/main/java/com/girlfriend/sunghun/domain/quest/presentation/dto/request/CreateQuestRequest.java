package com.girlfriend.sunghun.domain.quest.presentation.dto.request;

import com.girlfriend.sunghun.domain.quest.domain.type.Category;
import com.girlfriend.sunghun.domain.quest.domain.type.QuestType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class CreateQuestRequest {

    private String questTitle;
    private Category category;
    private QuestType type;

}
