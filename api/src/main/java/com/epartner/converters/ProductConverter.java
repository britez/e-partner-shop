package com.epartner.converters;

import com.epartner.domain.Product;
import com.epartner.domain.builders.ProductBuilder;
import com.epartner.representations.ProductRepresentation;
import com.epartner.representations.builders.ProductRepresentationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by maty on 1/9/16.
 */
@Component
public class ProductConverter {

    private final CategoryConverter categoryConverter;
    private final TechnicalSpecificationConverter technicalSpecificationConverter;
    private String baseImageUrl;

    @Autowired
    public ProductConverter(CategoryConverter categoryConverter,
                            TechnicalSpecificationConverter technicalSpecificationConverter,
                            @Value("${epartner.images.url}") String baseImageUrl){
        this.categoryConverter = categoryConverter;
        this.technicalSpecificationConverter = technicalSpecificationConverter;
        this.baseImageUrl = baseImageUrl;
    }

    public List<Product> convertList(List<ProductRepresentation> list){
        return list.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    public Product convert(ProductRepresentation productRepresentation) {
        return new ProductBuilder()
                .setId(productRepresentation.getId())
                .setDescription(productRepresentation.getDescription())
                .setStock(productRepresentation.getStock())
                .setName(productRepresentation.getName())
                .setPrice(productRepresentation.getPrice())
                .setCategory(
                        this.categoryConverter.convert(productRepresentation.getCategory()))
                .setTechnicalSpecification(
                        this.technicalSpecificationConverter.convertList(
                                productRepresentation.getTechnicalSpecifications()))
                .createProduct();
    }

    public ProductRepresentation convert(Product product){
        return new ProductRepresentationBuilder()
                .setId(product.getId())
                .setDescription(product.getDescription())
                .setPrincipalImage(product.getPrincipalImage())
                .setImages(product.getImages())
                .setName(product.getName())
                .setPrice(product.getPrice())
                .setStock(product.getStock())
                .setCategory(
                        this.categoryConverter
                                .convert(
                                        product.getCategory()
                                )
                )
                .setTechnicalSpecifications(
                        this.technicalSpecificationConverter.convert(product.getTechnicalSpecifications()))
                .createProductRepresentation(this.baseImageUrl);

    }

    public Page<ProductRepresentation> convert(Page<Product> page) {
        return new PageImpl<>(
                page
                    .getContent()
                    .stream()
                    .map(this::convert)
                    .collect(Collectors.toList())
        );
    }
}