package com.epartner.repositories;

import com.epartner.domain.Carousel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mapsi on 10/22/16.
 */
public interface CarouselRepository extends JpaRepository<Carousel, Long>{

    Page<Carousel> findAllByTitleContainingOrSubtitleContaining(String title, String subtitle, Pageable pageRequest);
}
