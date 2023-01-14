package com.girlfriend.sunghun.domain.auth.facade;

import com.girlfriend.sunghun.domain.auth.domain.User;
import com.girlfriend.sunghun.domain.auth.domain.repository.UserRepository;
import com.girlfriend.sunghun.domain.auth.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User queryUser(boolean withPersistence) {
        User withoutPersistence = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(withPersistence) {
            return userRepository.findById(withoutPersistence.getId())
                    .orElseThrow(() -> UserNotFoundException.EXCEPTION);
        }else {
            return withoutPersistence;
        }
    }

    public User queryUser() {
        return queryUser(false);
    }

    public User queryUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }



}