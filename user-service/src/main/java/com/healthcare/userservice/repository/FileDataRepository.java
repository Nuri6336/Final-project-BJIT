package com.healthcare.userservice.repository;

import com.healthcare.userservice.entity.FileData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileDataRepository extends JpaRepository<FileData,Long> {

    Optional<FileData> findByUniqueId(String uniqueId);
}
