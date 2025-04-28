package com.example.backendhoatuoiuit.repository;

import com.example.backendhoatuoiuit.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
    // CRUD sẵn có
}
