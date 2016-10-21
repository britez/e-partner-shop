package com.epartner.services;

import com.epartner.converters.ProductConverter;
import com.epartner.converters.TagConverter;
import com.epartner.domain.Product;
import com.epartner.domain.Tag;
import com.epartner.repositories.ProductRepository;
import com.epartner.repositories.TagRepository;
import com.epartner.representations.ProductRepresentation;
import com.epartner.representations.TagRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by martin on 24/09/16.
 */
@Service
public class TagService {

    private TagConverter converter;
    private TagRepository repository;
    private ProductConverter productConverter;
    private final ProductRepository productRepository;

    private final Integer MAX = 10;
    private final Integer PAGE = 0;

    @Autowired
    public TagService(
        TagConverter tagConverter,
        TagRepository repository,
        ProductConverter productConverter,
        ProductRepository productRepository){

        this.repository = repository;
        this.converter = tagConverter;
        this.productConverter = productConverter;
        this.productRepository = productRepository;
    }

    public TagRepresentation create(TagRepresentation tagRepresentation) {

        Tag tag = new Tag();
        tag.setIsCategory(tagRepresentation.getIsCategory());
        tag.setName(tagRepresentation.getName());

        tagRepresentation.getProducts().forEach(it -> tag.addProduct(this.productRepository.findOne(it.getId())));

        this.repository.save(tag);

        return this.converter.convert(tag);
    }

    public TagRepresentation update(TagRepresentation tagRepresentation, Long tagId) {
        Tag stored = Optional.ofNullable(this.repository.findOne(tagId)).orElseThrow(EntityNotFoundException::new);

        stored.getProducts().clear();

        tagRepresentation.getProducts().forEach(it -> {
            stored.addProduct(this.productRepository.getOne(it.getId()));
        });

        this.repository.save(stored);

        return this.converter.convert(stored);
    }

    public Page<TagRepresentation> list(
            Optional<Integer> max,
            Optional<Integer> page,
            Optional<Boolean> isCategory) {

        Page<Tag> stored;

        if(!isCategory.isPresent()) {
            stored = this.repository.findAll(this.buildPageRequest(max, page));
        } else {
            stored = this.repository.findAllByIsCategory(isCategory.get(), this.buildPageRequest(max, page));
        }

        return this.converter.convert(stored);
    }

    private PageRequest buildPageRequest(Optional<Integer> max, Optional<Integer> page){
        return new PageRequest(page.orElse(PAGE), max.orElse(MAX));
    }


    //TODO: No creo que necesitemos este servicio
    @Deprecated
    public Page<TagRepresentation> findAllTagByProduct(Long productId) {
        Product product = null;
        return new PageImpl<>(
                product
                    .getTags()
                    .stream()
                    .map(t -> {

                        TagRepresentation t2 = this.converter.convert(t);
                        t2.setProducts(new ArrayList<>());

                        return t2;
                    })
                    .filter(t1 -> !t1.getIsCategory())
                    .collect(Collectors.toList())
            );

    }

    public TagRepresentation getByName(String name) {
        return this.repository.findOneByName(name)
                .map(it -> this.converter.convert(it))
                .orElse(null);
    }

    public TagRepresentation get(Long id) {
        return this.converter.convert(Optional.ofNullable(this.repository.findOne(id)).orElseThrow(EntityExistsException::new));
    }
}