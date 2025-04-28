package com.example.backendhoatuoiuit.repository;

import com.example.backendhoatuoiuit.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
