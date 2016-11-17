package com.epartner.repositories;

import com.epartner.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mbritez on 28/08/16.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {


    Page<Category> findAllByNameContainingOrDescriptionContaining(String name, String description, Pageable pageRequest);
}
