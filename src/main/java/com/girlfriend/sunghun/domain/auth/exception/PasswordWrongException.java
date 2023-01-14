package com.girlfriend.sunghun.domain.auth.exception;

import com.girlfriend.sunghun.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class PasswordWrongException extends BusinessException {

    public static final PasswordWrongException EXCEPTION = new PasswordWrongException();

    private PasswordWrongException() {
        super(HttpStatus.BAD_REQUEST, "비밀번호가 잘못되었습니다.");
    }
}
