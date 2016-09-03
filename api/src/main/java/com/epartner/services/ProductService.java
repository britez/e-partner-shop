package com.epartner.services;

import com.epartner.converters.ProductConverter;
import com.epartner.domain.Category;
import com.epartner.domain.Product;
import com.epartner.repositories.ProductRepository;
import com.epartner.representations.CategoryRepresentation;
import com.epartner.representations.ProductRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

/**
 * Created by maty on 1/9/16.
 */
@Service
public class ProductService {

    private final ProductRepository repository;
    private final ProductConverter converter;
    private final CategoryService categoryService;

    private final Integer MAX = 10;
    private final Integer PAGE = 0;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          ProductConverter productConverter,
                          CategoryService categoryService){
        this.repository = productRepository;
        this.converter = productConverter;
        this.categoryService = categoryService;
    }

    public ProductRepresentation create(ProductRepresentation productRepresentation) {
        this.categoryService.show(productRepresentation.getCategory().getId());
        return this.converter.convert(
            this.repository.save(
                this.converter.convert(productRepresentation)
            ));
    }

    public ProductRepresentation update(ProductRepresentation productRepresentation, Long id) {
        Product product = this.get(id);
        //TODO agregar todos los updates
        product.setName(productRepresentation.getName());
        product.setDescription(productRepresentation.getDescription());
        this.repository.save(product);
        return this.converter.convert(product);
    }

    public ProductRepresentation show(Long id) {
        return this.converter.convert(this.get(id));
    }

    private Product get(Long id) {
        return Optional.ofNullable(this.repository.findOne(id)).orElseThrow(EntityNotFoundException::new);
    }

    public void delete(long id) {
        repository.delete(this.get(id));
    }

    public Page<ProductRepresentation> list(Optional<Integer> max, Optional<Integer> page) {
        Page<Product> stored = this.repository.findAll(
                new PageRequest(page.orElse(PAGE), max.orElse(MAX))
        );
        return this.converter.convert(stored);
    }
}