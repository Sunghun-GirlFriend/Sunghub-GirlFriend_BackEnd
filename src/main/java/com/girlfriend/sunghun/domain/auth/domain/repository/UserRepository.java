package com.girlfriend.sunghun.domain.auth.domain.repository;

import com.girlfriend.sunghun.domain.auth.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUserId(String userId);

    Page<User> findAll(Pageable pageable);

}
