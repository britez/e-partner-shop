package com.epartner.services;

import com.epartner.converters.CategoryConverter;
import com.epartner.domain.Category;
import com.epartner.repositories.CategoryRepository;
import com.epartner.representations.CategoryRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

/**
 * Created by mbritez on 28/08/16.
 */
@Service
public class CategoryService {

    private static final Integer PAGE = 0;
    private static final Integer MAX = 10;

    private final CategoryRepository categoryRepository;
    private final CategoryConverter converter;

    @Autowired
    public CategoryService(
            CategoryRepository categoryRepository,
            CategoryConverter converter) {
        this.categoryRepository = categoryRepository;
        this.converter = converter;
    }

    public Page<CategoryRepresentation> getAllCategories(final Optional<Integer> max, final Optional<Integer> page){
        Page<Category> stored = categoryRepository.findAll(
                new PageRequest(page.orElse(PAGE), max.orElse(MAX))
        );
        return this.converter.convert(stored);
    }

    public CategoryRepresentation create(CategoryRepresentation representation) {
        return converter.convert(categoryRepository.save(converter.convert(representation)));
    }

    public CategoryRepresentation update(CategoryRepresentation representation, Long id) {
        Category category = this.get(id);
        category.setDescription(representation.getDescription());
        category.setName(representation.getName());
        return converter.convert(categoryRepository.save(category));
    }

    public void delete(Long id) {
        Category category = this.get(id);
        categoryRepository.delete(category);
    }

    private Category get(Long id) {
        return Optional.ofNullable(categoryRepository.findOne(id)).orElseThrow(EntityNotFoundException::new);
    }
}
