package com.epartner.repositories;

import com.epartner.domain.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by martin on 24/09/16.
 */
public interface TagRepository  extends JpaRepository<Tag, Long> {

    Optional<Tag> findOneByName(String name);

    Page<Tag> findAllByIsCategory(Boolean isCategory, Pageable pageRequest);
}
