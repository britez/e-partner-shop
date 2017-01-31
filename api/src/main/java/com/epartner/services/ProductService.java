package com.epartner.services;

import com.epartner.converters.ProductConverter;
import com.epartner.converters.TechnicalSpecificationConverter;
import com.epartner.domain.Product;
import com.epartner.domain.ProductImage;
import com.epartner.exceptions.AlreadyImportedException;
import com.epartner.repositories.CategoryRepository;
import com.epartner.repositories.ProductRepository;
import com.epartner.representations.CategoryRepresentation;
import com.epartner.representations.ProductRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
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
    private final TagService tagService;
    private final TechnicalSpecificationConverter technicalSpecificationConverter;
    private final ProductImportService productImportService;

    private final Integer MAX = 10;
    private final Integer PAGE = 0;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          ProductConverter productConverter,
                          TagService tagService,
                          TechnicalSpecificationConverter technicalSpecificationConverter,
                          CategoryService categoryService,
                          StorageService storageService,
                          CategoryRepository categoryRepository,
                          ProductImportService productImportService) {

        this.repository = productRepository;
        this.converter = productConverter;
        this.technicalSpecificationConverter = technicalSpecificationConverter;
        this.categoryService = categoryService;
        this.storageService = storageService;
        this.categoryRepository = categoryRepository;
        this.tagService = tagService;
        this.productImportService = productImportService;
    }

    public ProductRepresentation create(ProductRepresentation productRepresentation) {
        //Checks if category exists
        this.categoryService.show(productRepresentation.getCategory().getId());
        //Checks if imported id exists
        this.checkAlreadyImported(productRepresentation);
        return this.converter.convert(
                this.repository.save(
                        this.converter.convert(productRepresentation)));
    }

    private void checkAlreadyImported(ProductRepresentation productRepresentation) {
        if(Optional.ofNullable(productRepresentation.getMeliId())
                .map(it -> this.repository.findOneByImportedId(it).isPresent())
                .orElse(false)) {
            throw new AlreadyImportedException();
        }
    }

    public ProductRepresentation update(ProductRepresentation productRepresentation, Long id) {
        Product product = this.get(id);
        product.setName(productRepresentation.getName());
        product.setDescription(productRepresentation.getDescription());
        product.setPrice(productRepresentation.getPrice());
        product.setStock(productRepresentation.getStock());
        product.setPublished(productRepresentation.getPublished());
        if(!product.getPublished()) {
            this.tagService.removeFromProduct(product);
            product.setTags(new ArrayList<>());
        }
        product.setCategory(this.categoryRepository.findOne(productRepresentation.getCategory().getId()));
        product.addTechnicalSpecifications(this.technicalSpecificationConverter.convertList(productRepresentation.getTechnicalSpecifications()));
        this.repository.save(product);
        return this.converter.convert(product);
    }

    public ProductRepresentation show(Long id) {

        Product stored = this.get(id);

        if(stored.getImported()){
            this.productImportService.fetch(stored);
        }

        return this.converter.convert(stored);
    }

    private Product get(Long id) {
        //return this.repository.findById(id)
        return Optional.ofNullable(this.repository.findOne(id))
                .orElseThrow(EntityNotFoundException::new);
    }

    public void delete(long id) {
        Product product = this.get(id);
        this.tagService.removeFromProduct(product);
        repository.delete(product);
    }

    public Page<ProductRepresentation> list(
            Optional<String> query, Optional<Integer> max, Optional<Integer> page) {

        Page<Product> stored;
        PageRequest pageRequest = new PageRequest(page.orElse(PAGE), max.orElse(MAX));

        if(query.isPresent()) {
            stored = this.repository.findAllByNameContainingOrDescriptionContaining(query.get(), query.get(), pageRequest);
        } else {
            stored = this.repository.findAll(pageRequest);
        }

        return this.fetch(stored, pageRequest);
    }

    private Page<ProductRepresentation> fetch(Page<Product> convert, Pageable pageRequest) {

        List<ProductRepresentation> result = new ArrayList<>();
        List<Product> toFetch = new ArrayList<>();

        convert.getContent().forEach(it -> {
            if(it.getImported()) {
                toFetch.add(it);
            } else {
                result.add(this.converter.convert(it));
            }
        });

        result.addAll(this.productImportService.fetch(toFetch));

        return new PageImpl<>(result, pageRequest, convert.getTotalElements());
    }


    public void addImage(Long id, List<MultipartFile> files) {
        Product product = this.get(id);

        files.forEach(file -> {
            ProductImage pi = createProductImage(file);
            product.addImage(pi);
        });

        saveImages(product);
    }

    public void addPrincipalImage(Long id, MultipartFile file) {
        Product product = this.get(id);
        product.setPrincipalImage(createProductImage(file));
        saveProduct(product);
    }

    public Page<ProductRepresentation> listByCategoryId(
            Long id, Optional<Integer> max, Optional<Integer> page) {
        Pageable pageRequest = new PageRequest(page.orElse(PAGE), max.orElse(MAX));
        Page<Product> stored = this.repository.findAllByCategory_id(id, pageRequest);
        return this.converter.convert(stored, pageRequest);
    }

    public Page<ProductRepresentation> listPublished(
            Optional<String> query,
            Optional<Integer> max, Optional<Integer> page) {


        Pageable pageRequest = new PageRequest(page.orElse(PAGE), max.orElse(MAX));
        
        String queryFilter = query.map(q -> "%"+q+"%").orElse("%");

        return this.converter.convert(
                this.repository.findAllByIsPublishedAndNameOrDescription(
                        true,
                        queryFilter,
                        queryFilter,
                        pageRequest),
                pageRequest);
    }

    public void create(Long categoryId, List<ProductRepresentation> products) {
        products.forEach(it -> {
            CategoryRepresentation cat = new CategoryRepresentation();
            cat.setId(categoryId);
            it.setCategory(cat);
            it.setImported(true);
            this.create(it);
        });
    }

    private void saveProduct(Product product) {
        try{
            this.repository.save(product);
        }catch(Exception e) {
            this.storageService.delete(product.getPrincipalImage().getFileName());
        }
    }

    private void saveImages(Product product) {
        try{
            this.repository.save(product);
        }catch(Exception e) {
            product.getImages().forEach(img -> this.storageService.delete(img.getFileName()));
        }
    }

    private ProductImage createProductImage(MultipartFile file) {
        return new ProductImage(this.storageService.store(file));
    }

}