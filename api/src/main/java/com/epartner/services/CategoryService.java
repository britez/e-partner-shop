package com.epartner.services;

import com.epartner.converters.CategoryConverter;
import com.epartner.domain.Category;
import com.epartner.exceptions.CategoryInUseException;
import com.epartner.repositories.CategoryRepository;
import com.epartner.repositories.ProductRepository;
import com.epartner.repositories.TagRepository;
import com.epartner.representations.CategoryRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.of;

/**
 * Created by mbritez on 28/08/16.
 */
@Service
public class CategoryService {

    private static final Integer PAGE = 0;
    private static final Integer MAX = 10;

    private final CategoryRepository categoryRepository;
    private final CategoryConverter converter;
    private final ProductRepository productRepository;
    private final TagService tagService;

    @Autowired
    public CategoryService(
            CategoryRepository categoryRepository,
            CategoryConverter converter,
            ProductRepository productRepository,
            TagService tagService) {
        this.categoryRepository = categoryRepository;
        this.converter = converter;
        this.productRepository = productRepository;
        this.tagService = tagService;
    }

    public Page<CategoryRepresentation> getAllCategories(final Optional<Integer> max, final Optional<Integer> page){
        Page<Category> stored = categoryRepository.findAll(
                new PageRequest(page.orElse(PAGE), max.orElse(MAX))
        );
        return this.customConverter(stored);
    }

    private Page<CategoryRepresentation> customConverter(Page<Category> categories) {
        PageImpl<CategoryRepresentation> result;
        List<CategoryRepresentation> content = categories
                .getContent()
                .stream()
                .map(this::checkProducts)
                .collect(Collectors.toList());
        result = new PageImpl<>(content);
        return result;
    }

    private CategoryRepresentation checkProducts(Category category) {
        CategoryRepresentation result = this.converter.convert(category);
        result.setTotalProducts(this.productRepository.countByCategoryAndIsPublished(category, true));
        return result;
    }

    public CategoryRepresentation create(CategoryRepresentation representation) {
        return converter.convert(categoryRepository.save(converter.convert(representation)));
    }

    public CategoryRepresentation update(CategoryRepresentation representation, Long id) {
        Category category = this.get(id);
        category.setDescription(representation.getDescription());
        category.setName(representation.getName());
        category.setHighlight(representation.getHighlight());
        return this.fetchTag(converter.convert(categoryRepository.save(category)));
    }

    public void delete(Long id) {
        Category category = this.get(id);

        if(productRepository.countByCategory(category) > 0) {
            throw new CategoryInUseException();
        }

        categoryRepository.delete(category);
    }

    public CategoryRepresentation show(Long id){
        Category category = this.get(id);
        return this.fetchTag(this.converter.convert(category));
    }

    private Category get(Long id) {
        return Optional.ofNullable(categoryRepository.findOne(id)).orElseThrow(EntityNotFoundException::new);
    }

    private CategoryRepresentation fetchTag(CategoryRepresentation category) {
        category.setTag(tagService.getByName(category.getName()));
        return category;
    }
}