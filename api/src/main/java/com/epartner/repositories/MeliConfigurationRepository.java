package com.epartner.repositories;

import com.epartner.domain.MeliConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mapsi on 12/14/16.
 */
public interface MeliConfigurationRepository extends JpaRepository<MeliConfiguration, Long> {
}
