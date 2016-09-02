package com.epartner.repositories;

import com.epartner.domain.Product;
import com.epartner.representations.ProductRepresentation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by maty on 1/9/16.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

}
