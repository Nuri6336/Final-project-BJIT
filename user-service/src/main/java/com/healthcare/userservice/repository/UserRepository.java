package com.healthcare.userservice.repository;


import com.healthcare.userservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUserId(Long userId);
    Optional<UserEntity> findByUniqueId(String uniqueId);

}
