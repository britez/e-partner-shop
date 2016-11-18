package com.epartner.services;

import com.epartner.domain.Category;
import com.epartner.domain.MeiSearchResponse;
import com.epartner.domain.MeliItem;
import com.epartner.representations.CategoryRepresentation;
import com.epartner.representations.ProductImageRepresentation;
import com.epartner.representations.ProductRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by maty on 1/9/16.
 */
@Service
public class ProductImportService {

    private static final String ITEM_URL = "https://api.mercadolibre.com/items?ids=";
    private static final String URL =
            "https://api.mercadolibre.com/sites/MLA/search?seller_id=165975732&" +
                    "limit=LIMIT&" +
                    "offset=OFFSET&" +
                    "q=QUERY";
    private static final Integer MAX = 10;
    private static final Integer PAGE = 0;

    private RestTemplate template;
    private ProductService productService;

    @Autowired
    public ProductImportService(ProductService productService) {
        this.productService = productService;
        this.template = new RestTemplate();
    }

    public Page<ProductRepresentation> list(Optional<String> query, Optional<Integer> max, Optional<Integer> page) {

        Pageable pageRequest = new PageRequest(page.orElse(PAGE), max.orElse(MAX));

        String url = URL
                .replace("LIMIT", max.orElse(MAX).toString())
                .replace("OFFSET", ((Integer)(page.orElse(PAGE) * max.orElse(MAX))).toString())
                .replace("QUERY", query.orElse(""));

        ResponseEntity<MeiSearchResponse> response = this.template.getForEntity(url, MeiSearchResponse.class);

        if(!HttpStatus.OK.equals(response.getStatusCode())) {
            //TODO: Throw exception
        }

        List<MeliItem> simpleItems = response.getBody().getResults();

        String ids = simpleItems
            .stream()
            .map(MeliItem::getId)
            .collect(Collectors.joining(","));

        ResponseEntity<MeliItem[]> itemsResponse =
                this.template.getForEntity(ITEM_URL + ids, MeliItem[].class);

        if(!HttpStatus.OK.equals(itemsResponse.getStatusCode())) {
            //TODO: Throw exception
        }

        List<MeliItem> items = Arrays.asList(itemsResponse.getBody());

        List<ProductRepresentation> result = items
            .stream()
            .map(this::convert)
            .collect(Collectors.toList());

        return new PageImpl<>(result, pageRequest, response.getBody().getPaging().getTotal());
    }

    private ProductRepresentation convert(MeliItem meliItem) {
        ProductRepresentation result = new ProductRepresentation();
        result.setMeliId(meliItem.getId());
        result.setName(meliItem.getTitle());
        result.setPrice(meliItem.getPrice());
        result.setStock(meliItem.getAvailable_quantity());
        result.setTechnicalSpecifications(new ArrayList<>());

        ProductImageRepresentation principalImage = new ProductImageRepresentation();
        principalImage.setUrl(meliItem.getPictures().get(0).getUrl());

        result.setPrincipalImage(principalImage);
        //TODO: Agregar las imagenes restantes
        //TODO: Chequear que el producto no exista

        return result;
    }

    public void create(Long categoryId, List<ProductRepresentation> products) {
        products.forEach(it -> {
            CategoryRepresentation cat = new CategoryRepresentation();
            cat.setId(categoryId);
            it.setCategory(cat);
            this.productService.create(it);
        });
    }
}