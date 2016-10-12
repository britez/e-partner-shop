package com.epartner.converters;

import com.epartner.domain.Category;
import com.epartner.representations.CategoryRepresentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by mbritez on 29/08/16.
 */
@Component
public class CategoryConverter {

    public Page<CategoryRepresentation> convert(Page<Category> page) {
        PageImpl<CategoryRepresentation> result;
        List<CategoryRepresentation> content = page
                .getContent()
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
        result = new PageImpl<>(content);
        return result;
    }

    public CategoryRepresentation convert(Category category) {
        CategoryRepresentation result = new CategoryRepresentation();
        result.setId(category.getId());
        result.setDescription(category.getDescription());
        result.setName(category.getName());
        result.setHighlight(category.getHighlight());
        return result;
    }

    public Category convert(CategoryRepresentation category) {
        Category result = new Category();
        result.setDescription(category.getDescription());
        if(Optional.ofNullable(category.getId()).isPresent()) {
            result.setId(category.getId());
        }
        result.setHighlight(category.getHighlight());
        result.setName(category.getName());
        return result;
    }
}