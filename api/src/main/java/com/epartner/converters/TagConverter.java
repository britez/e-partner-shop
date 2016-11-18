package com.epartner.converters;

import com.epartner.domain.Tag;
import com.epartner.domain.builders.TagBuilder;
import com.epartner.representations.TagRepresentation;
import com.epartner.representations.builders.TagRepresentationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Created by martin on 24/09/16.
 */
@Component
public class TagConverter {

    private final ProductConverter productConverter;

    @Autowired
    public TagConverter(ProductConverter productConverter){
        this.productConverter = productConverter;
    }

    public TagRepresentation convert(Tag toConvert){
        return new TagRepresentationBuilder()
                .setId(toConvert.getId())
                .setIsCategory(toConvert.getIsCategory())
                .setProducts(
                        toConvert
                                .getProducts()
                                .stream()
                                .map(productConverter::convert)
                                .collect(Collectors.toList())

                )
                .setName(toConvert.getName())
                .createTag();
    }

    public Tag convert(TagRepresentation  tagRepresentation){
        return new TagBuilder()
                .setId(tagRepresentation.getId())
                .setIsCategory(tagRepresentation.getIsCategory())
                .setProducts(this.productConverter.convertList(tagRepresentation.getProducts()))
                .setName(tagRepresentation.getName())
                .createTag();

    }

    public Page<TagRepresentation> convert(Page<Tag> page, Pageable pageable) {
        return new PageImpl<>(
                page
                .getContent()
                .stream()
                .map(this::convert)
                .collect(Collectors.toList()),
                pageable,
                page.getTotalElements()
        );
    }
}
