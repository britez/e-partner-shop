package com.epartner.repositories;

import com.epartner.domain.Product;
import com.epartner.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by martin on 24/09/16.
 */
public interface TagRepository  extends JpaRepository<Tag, Long> {
}
