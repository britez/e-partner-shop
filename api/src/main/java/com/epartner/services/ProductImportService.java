package com.epartner.services;

import com.epartner.converters.ProductConverter;
import com.epartner.domain.MeiSearchResponse;
import com.epartner.domain.MeliConfiguration;
import com.epartner.domain.MeliItem;
import com.epartner.domain.Product;
import com.epartner.exceptions.MeliNotConfiguredException;
import com.epartner.repositories.MeliConfigurationRepository;
import com.epartner.repositories.ProductRepository;
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

import java.util.Arrays;
import java.util.Collections;
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
    private static final String CODE_URL = "https://api.mercadolibre.com/oauth/token?grant_type=authorization_code" +
            "&client_id=8319105886566033&client_secret=GJUZQ9QZS5jIOFZqQVN7EZAqwgLqv0kc" +
            "&code=CODE&redirect_uri=https://example.com";
    private static final Integer MAX = 10;
    private static final Integer PAGE = 0;

    private RestTemplate template;
    private ProductRepository productRepository;
    private ProductConverter productConverter;
    private MeliConfigurationRepository meliConfigurationRepository;

    @Autowired
    public ProductImportService(
            ProductRepository productRepository,
            ProductConverter productConverter,
            MeliConfigurationRepository meliConfigurationRepository) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
        this.meliConfigurationRepository = meliConfigurationRepository;
        this.template = new RestTemplate();
    }

    public Page<ProductRepresentation> list(
            Optional<String> query,
            Optional<Integer> max,
            Optional<Integer> page) {

        Pageable pageRequest = new PageRequest(page.orElse(PAGE), max.orElse(MAX));

        checkAccessToken();

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

        List<ProductRepresentation> result = this.listByIds(ids);

        return new PageImpl<>(result, pageRequest, response.getBody().getPaging().getTotal());
    }

    private void checkAccessToken() {
        if(this.meliConfigurationRepository.findAll().isEmpty()){
            throw new MeliNotConfiguredException();
        }
    }

    public ProductRepresentation fetch(Product stored) {
        ProductRepresentation result = this.listByIds(stored.getImportedId()).get(0);
        ProductRepresentation request = this.productConverter.convert(stored);
        result.setId(request.getId());
        result.setCategory(request.getCategory());
        return result;
    }

    public List<ProductRepresentation> fetch(List<Product> products) {
        List<ProductRepresentation> meliProducts = this.listByIds(
                products
                        .stream()
                        .map(Product::getImportedId)
                        .collect(Collectors.joining(",")));

        meliProducts.forEach(it ->
                it.setId(products
                        .stream()
                        .filter(prod -> prod.getImportedId().equals(it.getMeliId()))
                        .findFirst()
                        .get()
                        .getId()));

        return meliProducts;
    }

    private List<ProductRepresentation> listByIds(String ids) {

        if("".equals(ids)) {
            return Collections.emptyList();
        }

        ResponseEntity<MeliItem[]> itemsResponse =
                this.template.getForEntity(ITEM_URL + ids, MeliItem[].class);

        if(!HttpStatus.OK.equals(itemsResponse.getStatusCode())) {
            //TODO: Throw exception
        }

        List<MeliItem> items = Arrays.asList(itemsResponse.getBody());

        return items
                .stream()
                .map(it -> this.productConverter.convert(
                        it,
                        this.productRepository.findOneByImportedId(it.getId()).isPresent()))
                .collect(Collectors.toList());
    }

    public void saveConfig(String code) {
        ResponseEntity<MeliConfiguration> result = this.template.postForEntity(CODE_URL.replace("CODE", code), null, MeliConfiguration.class);
        if(HttpStatus.OK.equals(result.getStatusCode())) {
            MeliConfiguration config = result.getBody();
            this.meliConfigurationRepository.save(config);
        }
    }
}