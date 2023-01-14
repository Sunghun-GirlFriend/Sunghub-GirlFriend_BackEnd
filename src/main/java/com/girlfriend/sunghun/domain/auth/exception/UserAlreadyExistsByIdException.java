package com.girlfriend.sunghun.domain.auth.exception;

import com.girlfriend.sunghun.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsByIdException extends BusinessException {

    public static final UserAlreadyExistsByIdException EXCEPTION = new UserAlreadyExistsByIdException();

    private UserAlreadyExistsByIdException() {
        super(HttpStatus.CONFLICT, "아이디가 이미 존재합니다.");
    }
}
