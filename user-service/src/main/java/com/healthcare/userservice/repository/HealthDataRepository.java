package com.healthcare.userservice.repository;

import com.healthcare.userservice.entity.HealthDataEntity;
import com.healthcare.userservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HealthDataRepository extends JpaRepository<HealthDataEntity, Long> {
    HealthDataEntity findTopByUserEntityOrderByTimestampDesc(UserEntity currentUser);
    List<HealthDataEntity> findAllByUserEntity(UserEntity currentUser);
}
