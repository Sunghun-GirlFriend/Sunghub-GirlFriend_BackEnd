package com.girlfriend.sunghun.global.security;

import com.girlfriend.sunghun.domain.auth.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class UserToken extends UsernamePasswordAuthenticationToken {

    public UserToken(User user) {
        super(user, null, null);
    }

}
