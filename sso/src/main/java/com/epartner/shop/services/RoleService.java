package com.epartner.shop.services;

import com.epartner.shop.converters.RoleConverter;
import com.epartner.shop.domain.Role;
import com.epartner.shop.repositories.RoleRepository;
import com.epartner.shop.representations.RoleRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

/**
 * Created by martin on 27/12/16.
 */
@Service
public class RoleService {

    private RoleRepository repository;
    private RoleConverter converter;

    @Autowired
    public RoleService(RoleRepository repository,
                       RoleConverter converter){
        this.repository = repository;
        this.converter = converter;
    }

    public RoleRepresentation create(RoleRepresentation roleRepresentation) {
        return this.converter.convert(this.repository.save(this.converter.convert(roleRepresentation)));
    }


}
