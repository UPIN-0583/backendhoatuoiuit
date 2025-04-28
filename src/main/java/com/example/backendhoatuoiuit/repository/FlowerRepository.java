package com.example.backendhoatuoiuit.repository;

import com.example.backendhoatuoiuit.entity.Flower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlowerRepository extends JpaRepository<Flower, Integer> {
}
