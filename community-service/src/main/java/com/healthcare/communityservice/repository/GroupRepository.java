package com.healthcare.communityservice.repository;

import com.healthcare.communityservice.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {

    Optional<Group> findByGroupName(String groupName);
    Group findByGroupNameAndUserId(String groupName, Long id);
}
