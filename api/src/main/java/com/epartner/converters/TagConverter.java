package com.epartner.converters;

import com.epartner.domain.Tag;
import com.epartner.domain.builders.TagBuilder;
import com.epartner.representations.TagRepresentation;
import com.epartner.representations.TagRepresentationBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by martin on 24/09/16.
 */
@Component
public class TagConverter {


    public TagRepresentation convert(Tag toConvert){


        return new TagRepresentationBuilder()
                .setId(toConvert.getId())
                .setIsCategory(toConvert.getIsCategory())
                .setProducts(toConvert.getProducts())
                .setName(toConvert.getName())
                .createTag();
    }

    public Tag convert(TagRepresentation  tagRepresentation){


        return new TagBuilder()
                .setId(tagRepresentation.getId())
                .setIsCategory(tagRepresentation.getIsCategory())
                .setProducts(tagRepresentation.getProductRepresentationList())
                .setName(tagRepresentation.getName())
                .createTag();

    }
}
