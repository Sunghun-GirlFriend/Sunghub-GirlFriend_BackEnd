package com.girlfriend.sunghun.domain.quest.exception;

import com.girlfriend.sunghun.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class QuestTimeNotPassedException extends BusinessException {

    public static final QuestTimeNotPassedException EXCEPTION = new QuestTimeNotPassedException();

    private QuestTimeNotPassedException() {
        super(HttpStatus.BAD_REQUEST, "시간이 끝나지 않았습니다.");
    }
}
