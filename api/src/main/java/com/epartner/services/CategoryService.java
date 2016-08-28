package com.epartner.services;

import com.epartner.domain.Category;
import com.epartner.repositories.CategoryRepository;
import com.epartner.representations.CategoryRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by mbritez on 28/08/16.
 */
@Service
public class CategoryService {

    private static final Integer PAGE = 0;
    private static final Integer MAX = 10;

    private CategoryRepository categoryRepository;


    @Autowired
    public CategoryService(CategoryRepository categoryRepository ) {
        this.categoryRepository = categoryRepository;
    }

    public Page<Category> getAllCategories(final Optional<Integer> max, final Optional<Integer> page){
        return categoryRepository.findAll(
                new PageRequest(page.orElse(PAGE), max.orElse(MAX))
        );
    }

    public CategoryRepresentation create(CategoryRepresentation representation) {
        return categoryRepository.save(representation.convert()).convert();
    }

    public CategoryRepresentation update(CategoryRepresentation representation, Long id) {
        Category category = categoryRepository.findOne(id);
        category.setDescription(representation.getDescription());
        category.setName(representation.getName());
        return categoryRepository.save(category).convert();
    }
}
