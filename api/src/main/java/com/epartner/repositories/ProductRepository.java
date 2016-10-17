package com.epartner.repositories;

import com.epartner.domain.Category;
import com.epartner.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by maty on 1/9/16.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByCategory(Category id, Pageable pageRequest);

    long countByCategory(Category id);
}
