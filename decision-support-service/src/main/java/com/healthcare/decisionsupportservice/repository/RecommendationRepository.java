package com.healthcare.decisionsupportservice.repository;

import com.healthcare.decisionsupportservice.entity.RecommendationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendationRepository extends JpaRepository<RecommendationEntity, Long> {

    List<RecommendationEntity> findByRecommendationTypeAndRecommendationCategory(String bmiCategory, String diet);
}
