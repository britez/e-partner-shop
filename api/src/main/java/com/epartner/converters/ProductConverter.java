package com.epartner.converters;

import com.epartner.domain.Product;
import com.epartner.domain.builders.ProductBuilder;
import com.epartner.representations.ProductRepresentation;
import com.epartner.representations.ProductRepresentationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public ProductConverter(CategoryConverter categoryConverter, TechnicalSpecificationConverter technicalSpecificationConverter){
        this.categoryConverter = categoryConverter;
        this.technicalSpecificationConverter = technicalSpecificationConverter;
    }

    public Product convert(ProductRepresentation productRepresentation) {

        return new ProductBuilder()
                .setId(productRepresentation.getId())
                .setDescription(productRepresentation.getDescription())
                .setStock(productRepresentation.getStock())
                .setName(productRepresentation.getName())
                .setPrice(productRepresentation.getPrice())
                .setCategory(this.categoryConverter.convert(productRepresentation.getCategory()))
                .createProduct();


    }

    public ProductRepresentation convert(Product product){

        return new ProductRepresentationBuilder()
                .setId(product.getId())
                .setDescription(product.getDescription())
                .setImages(product.getImages())
                .setName(product.getName())
                .setPrice(product.getPrice())
                .setStock(product.getStock())
                .setCategoryRepresentation(
                        this.categoryConverter
                                .convert(
                                        product.getCategory()
                                )
                )
                .setThenicalSpecification(
                        this.technicalSpecificationConverter.convert(product.getTechnicalSpecifications()))
                .createProductRepresentation();

    }

    public Page<ProductRepresentation> convert(Page<Product> page) {
        PageImpl<ProductRepresentation> result;
        List<ProductRepresentation> content = page
                .getContent()
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
        result = new PageImpl<>(content);
        return result;
    }

}
