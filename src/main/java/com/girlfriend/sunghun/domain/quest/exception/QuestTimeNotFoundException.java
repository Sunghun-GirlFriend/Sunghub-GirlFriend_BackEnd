package com.girlfriend.sunghun.domain.quest.exception;

import com.girlfriend.sunghun.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class QuestTimeNotFoundException extends BusinessException {

    public static final QuestTimeNotFoundException EXCEPTION = new QuestTimeNotFoundException();

    private QuestTimeNotFoundException() {
        super(HttpStatus.NOT_FOUND, "퀘스트 시간을 찾을 수 없습니다.");
    }
}
