package com.epartner.repositories;

import com.epartner.domain.Category;
import com.epartner.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Created by maty on 1/9/16.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    long countByCategoryAndIsPublished(Category id, Boolean isPublished);

    long countByCategory(Category id);

    Page<Product> findAllByIsPublished(Boolean aBoolean, Pageable pageRequest);

    Optional<Product> findOneByImportedId(String importedId);

    Page<Product> findAllByNameContainingOrDescriptionContaining(String name, String description, Pageable pageRequest);

    @Query("select p from Product p where (p.name like ?2 or p.description like ?3) and p.isPublished = ?1")
    Page<Product> findAllByIsPublishedAndNameOrDescription(Boolean published, String name, String description, Pageable pageRequest);

    @Query("select p from Product p where p.id = 1 and (p.stock > 1 or p.isImported = true)")
    Optional<Product> findById(Long id);

    Page<Product> findAllByCategory_id(Long id, Pageable pageRequest);
}
