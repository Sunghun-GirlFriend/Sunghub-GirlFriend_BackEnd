package com.girlfriend.sunghun.domain.quest.exception;

import com.girlfriend.sunghun.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class QuestNotFoundException extends BusinessException {

    public static final QuestNotFoundException EXCEPTION = new QuestNotFoundException();

    private QuestNotFoundException() {
        super(HttpStatus.NOT_FOUND, "퀘스트를 찾을 수 없습니다.");
    }
}
