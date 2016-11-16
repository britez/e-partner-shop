package com.epartner.services;

import com.epartner.converters.CarouselConverter;
import com.epartner.domain.Carousel;
import com.epartner.domain.ProductImage;
import com.epartner.repositories.CarouselRepository;
import com.epartner.representations.CarouselRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityExistsException;
import java.util.Optional;

/**
 * Created by mapsi on 10/22/16.
 */
@Service
public class CarouselService {

    private static final Integer PAGE = 0;
    private static final Integer MAX = 10;

    private final CarouselRepository carouselRepository;
    private final CarouselConverter converter;
    private final StorageService storageService;

    @Autowired
    public CarouselService(
            CarouselRepository carouselRepository,
            CarouselConverter converter,
            StorageService storageService) {
        this.carouselRepository = carouselRepository;
        this.converter = converter;
        this.storageService = storageService;
    }

    public Page<CarouselRepresentation> getAll(
            Optional<String> query,
            Optional<Integer> max,
            Optional<Integer> page) {
        Pageable pageRequest = new PageRequest(page.orElse(PAGE), max.orElse(MAX));
        Page<Carousel> stored;
        if(query.isPresent()){
            stored = carouselRepository.findAllByTitleContainingOrSubtitleContaining(
                    query.get(),
                    query.get(),
                    pageRequest);
        } else {
            stored = carouselRepository.findAll(pageRequest);
        }

        return this.converter.convert(stored, pageRequest);

    }

    public CarouselRepresentation show(Long id) {
        Carousel carousel = this.get(id);
        return this.converter.convert(carousel);
    }

    private Carousel get(Long id) {
        return Optional.ofNullable(this.carouselRepository.findOne(id)).orElseThrow(EntityExistsException::new);
    }

    public CarouselRepresentation create(CarouselRepresentation representation) {
        return this.converter.convert(this.carouselRepository.save(this.converter.convert(representation)));
    }

    public CarouselRepresentation update(CarouselRepresentation representation, Long id) {
        Carousel stored = this.get(id);
        stored.setTitle(representation.getTitle());
        stored.setTitleUrl(representation.getTitleUrl());
        stored.setSubtitle(representation.getSubtitle());
        stored.setSubtitleUrl(representation.getSubtitleUrl());
        //TODO Update images
        this.carouselRepository.save(stored);
        return this.converter.convert(stored);
    }

    public void delete(Long id) {
        this.carouselRepository.delete(this.get(id));
    }

    public void addImage(Long id, MultipartFile file, Boolean isPrincipal) {
        Carousel stored = this.get(id);
        ProductImage image = new ProductImage(this.storageService.store(file));

        if(isPrincipal) {
            stored.setPrincipalImage(image);
        } else {
            stored.setBackgroundImage(image);
        }

        try {
            this.carouselRepository.save(stored);
        } catch (Exception e) {
            this.storageService.delete(image.getFileName());
        }
    }

}
