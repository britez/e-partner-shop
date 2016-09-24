package com.epartner.services;

import com.epartner.converters.ProductConverter;
import com.epartner.converters.TechnicalSpecificationConverter;
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
import java.util.List;
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
    private final TechnicalSpecificationConverter technicalSpecificationConverter;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          ProductConverter productConverter,
                          TechnicalSpecificationConverter technicalSpecificationConverter,
                          CategoryService categoryService,
                          StorageService storageService,
                          CategoryRepository categoryRepository) {

        this.repository = productRepository;
        this.converter = productConverter;
        this.technicalSpecificationConverter = technicalSpecificationConverter;
        this.categoryService = categoryService;
        this.storageService = storageService;
        this.categoryRepository = categoryRepository;
    }

    public ProductRepresentation create(ProductRepresentation productRepresentation) {
        this.categoryService.show(productRepresentation.getCategory().getId());
        Product productToSave = this.converter.convert(productRepresentation);
        productToSave.addTechnicalSpecifications(this.technicalSpecificationConverter.convertList(productRepresentation.getTechnicalSpecifications()));
        //TODO MEjorar esto
        Product productSaved = this.repository.save(productToSave);
        return this.converter.convert(productSaved);
    }

    public ProductRepresentation update(ProductRepresentation productRepresentation, Long id) {
        Product product = this.get(id);
        //TODO agregar todos los updates
        product.setName(productRepresentation.getName());
        product.setDescription(productRepresentation.getDescription());
        product.setPrice(productRepresentation.getPrice());
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
        ProductImage productImage = new ProductImage(createProductImage(file), product);
        saveImage(product, productImage);
    }

    public void addPrincipalImage(Long id, MultipartFile file) {
        Product product = this.get(id);
        ProductImage principalImage = new ProductImage(createProductImage(file), product);
        principalImage.setIsPrincipal(true);
        saveImage(product, principalImage);
    }

    public Page<ProductRepresentation> listByCategoryId(
            Long id, Optional<Integer> max, Optional<Integer> page) {
        Category param = new Category();
        param.setId(id);
        return this.converter.convert(
                this.repository.findAllByCategory(param, new PageRequest(page.orElse(PAGE), max.orElse(MAX))));
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

    private String createProductImage(MultipartFile file) {
        return this.storageService.store(file);
    }
}