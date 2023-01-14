package com.girlfriend.sunghun.domain.quest.exception;

import com.girlfriend.sunghun.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class QuestAlreadyExistsException extends BusinessException {

    public static final QuestAlreadyExistsException EXCEPTION = new QuestAlreadyExistsException();

    private QuestAlreadyExistsException() {
        super(HttpStatus.CONFLICT, "퀘스트가 이미 존재합니다.");
    }
}
