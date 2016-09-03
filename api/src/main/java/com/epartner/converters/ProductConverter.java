package com.epartner.converters;

import com.epartner.domain.Category;
import com.epartner.domain.Product;
import com.epartner.domain.builders.ProductBuilder;
import com.epartner.representations.CategoryRepresentation;
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

    @Autowired
    public ProductConverter(CategoryConverter categoryConverter){
        this.categoryConverter = categoryConverter;
    }

    public Product convert(ProductRepresentation productRepresentation) {

        return new ProductBuilder()
                .setId(productRepresentation.getId())
                .setDescription(productRepresentation.getDescription())
                .setStock(productRepresentation.getStock())
                .setName(productRepresentation.getName())
                .setImage(productRepresentation.getImage())
                .setCategory(this.categoryConverter.convert(productRepresentation.getCategory()))
                .setTechnicaSpeficication(productRepresentation.getTechnicaSpeficication())
                .createProduct();


    }

    public ProductRepresentation convert(Product product){

        return new ProductRepresentationBuilder()
                .setId(product.getId())
                .setDescription(product.getDescription())
                .setImage(product.getImage())
                .setName(product.getName())
                .setStock(product.getStock())
                //.setTechnicaSpeficication(product.getTechnicaSpeficication())
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
