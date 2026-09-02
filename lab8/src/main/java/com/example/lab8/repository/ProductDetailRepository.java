package com.example.lab8.repository;

import com.example.lab8.model.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailRepository
        extends JpaRepository<ProductDetail, Long> {
}