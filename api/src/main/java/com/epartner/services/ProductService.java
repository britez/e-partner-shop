package com.epartner.services;

import com.epartner.converters.ProductConverter;
import com.epartner.domain.Category;
import com.epartner.domain.Product;
import com.epartner.domain.ProductImage;
import com.epartner.repositories.CategoryRepository;
import com.epartner.repositories.ProductRepository;
import com.epartner.representations.ProductRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private final StorageService storageService;
    private final CategoryRepository categoryRepository;

    private final Integer MAX = 10;
    private final Integer PAGE = 0;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          ProductConverter productConverter,
                          CategoryService categoryService,
                          StorageService storageService,
                          CategoryRepository categoryRepository) {

        this.repository = productRepository;
        this.converter = productConverter;
        this.categoryService = categoryService;
        this.storageService = storageService;
        this.categoryRepository = categoryRepository;
    }

    public ProductRepresentation create(ProductRepresentation productRepresentation) {

        //Esto esta duplicado
        this.categoryService.show(productRepresentation.getCategory().getId());
        Category c = this.categoryRepository.findOne(productRepresentation.getCategory().getId());
        Product p = this.converter.convert(productRepresentation);
        p.setCategory(c);
        return this.converter.convert(
            this.repository.save(
                p
            ));
    }

    public ProductRepresentation update(ProductRepresentation productRepresentation, Long id) {
        Product product = this.get(id);
        //TODO agregar todos los updates
        product.setName(productRepresentation.getName());
        product.setDescription(productRepresentation.getDescription());
        product.setStock(productRepresentation.getStock());
        product.setCategory(this.categoryRepository.findOne(productRepresentation.getCategory().getId()));
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

    public void addImage(Long id, MultipartFile file) {

        Product product = this.get(id);
        ProductImage productImage = new ProductImage(createProductImage(id, file), product);
        saveImage(product, productImage);
    }

    public void addPrincipalImage(Long id, MultipartFile file) {
        Product product = this.get(id);
        ProductImage principalImage = new ProductImage(createProductImage(id, file), product);
        principalImage.setIsPrincipal(true);
        saveImage(product, principalImage);
    }

    private void saveImage(Product product, ProductImage productImage) {
        product.addImage(productImage);
        try{
            this.repository.save(product);
        }catch(Exception e) {
            //TODO: agregar el logger
            this.storageService.delete(productImage.getFileName());
        }
    }

    private String createProductImage(Long id, MultipartFile file) {
        return this.storageService.store(file);
    }
}