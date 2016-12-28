package com.epartner.shop.repositories;

import com.epartner.shop.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by martin on 27/12/16.
 */
public interface RoleRepository extends JpaRepository<Role, String> {


    Role findByAuthority(String role);
}
