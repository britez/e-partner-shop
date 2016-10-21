package com.epartner.repositories;

import com.epartner.domain.Category;
import com.epartner.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by maty on 1/9/16.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByCategoryAndIsPublished(Category id, Boolean isPublished, Pageable pageRequest);

    long countByCategoryAndIsPublished(Category id, Boolean isPublished);

    long countByCategory(Category id);

    Page<Product> findAllByIsPublished(Boolean aBoolean, Pageable pageRequest);
}
