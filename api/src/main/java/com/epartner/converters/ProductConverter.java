package com.epartner.converters;

import com.epartner.domain.MeliItem;
import com.epartner.domain.MeliItemPicture;
import com.epartner.domain.Product;
import com.epartner.domain.ProductImage;
import com.epartner.domain.builders.ProductBuilder;
import com.epartner.representations.ProductImageRepresentation;
import com.epartner.representations.ProductRepresentation;
import com.epartner.representations.builders.ProductRepresentationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        Product product = new ProductBuilder()
                .setId(productRepresentation.getId())
                .setDescription(productRepresentation.getDescription())
                .setStock(productRepresentation.getStock())
                .setName(productRepresentation.getName())
                .setPrice(productRepresentation.getPrice())
                .setImported(Optional.ofNullable(productRepresentation.getImported()).orElse(false))
                .setImportedId(Optional.ofNullable(productRepresentation.getMeliId()).orElse(null))
                .setPublished(Optional.ofNullable(productRepresentation.getPublished()).orElse(true))
                .setCategory(
                        this.categoryConverter.convert(productRepresentation.getCategory()))
                .setTechnicalSpecification(
                        this.technicalSpecificationConverter.convertList(
                                productRepresentation.getTechnicalSpecifications()))
                .createProduct();

        bindImage(productRepresentation, product);

        return product;
    }

    private void bindImage(ProductRepresentation productRepresentation, Product product) {
        if(Optional.ofNullable(productRepresentation.getPrincipalImage()).isPresent()) {
            ProductImage image = new ProductImage();
            image.setUrl(productRepresentation.getPrincipalImage().getUrl());
            product.setPrincipalImage(image);
        }
    }

    public ProductRepresentation convert(Product product){
        return new ProductRepresentationBuilder()
                .setId(product.getId())
                .setDescription(product.getDescription())
                .setPrincipalImage(product.getPrincipalImage())
                .setImages(product.getImages())
                .setMeliId(product.getImportedId())
                .setName(product.getName())
                .setImported(Optional.ofNullable(product.getImported()).orElse(false))
                .setPrice(product.getPrice())
                .setStock(product.getStock())
                .setPublished(Optional.ofNullable(product.getPublished()).orElse(true))
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

    public Page<ProductRepresentation> convert(Page<Product> page, Pageable pageRequest) {
        return new PageImpl<>(
                page
                    .getContent()
                    .stream()
                    .map(this::convert)
                    .collect(Collectors.toList()),
                pageRequest,
                page.getTotalElements()
        );
    }

    public ProductRepresentation convert(MeliItem meliItem, Boolean alreadyImported) {
        ProductRepresentation result = new ProductRepresentation();
        result.setMeliId(meliItem.getId());
        result.setName(meliItem.getTitle());
        result.setPrice(meliItem.getPrice());
        result.setStock(meliItem.getAvailable_quantity());
        result.setTechnicalSpecifications(new ArrayList<>());

        result.setPrincipalImage(convertImage(meliItem.getPictures().get(0)));
        result.setImages(convertImages(meliItem.getPictures()));
        result.setImported(alreadyImported);

        return result;
    }

    private ProductImageRepresentation convertImage(MeliItemPicture picture) {
        ProductImageRepresentation principalImage = new ProductImageRepresentation();
        principalImage.setUrl(picture.getUrl());
        return principalImage;
    }

    private List<ProductImageRepresentation> convertImages(List<MeliItemPicture> pictures) {
        return pictures
                .subList(1,pictures.size() - 1)
                .stream()
                .map(this::convertImage)
                .collect(Collectors.toList());
    }
}