package com.epartner.shop.converters;

import com.epartner.shop.domain.Role;
import com.epartner.shop.representations.RoleRepresentation;
import org.springframework.stereotype.Component;

/**
 * Created by martin on 27/12/16.
 */
@Component
public class RoleConverter {

    public Role convert (RoleRepresentation roleRepresentation){
        Role result = new Role();
        result.setAuthority(roleRepresentation.getAuthorities());
        return result;
    }

    public RoleRepresentation convert (Role role){
        RoleRepresentation result = new RoleRepresentation();
        result.setAuthorities(role.getAuthority());
        return result;
    }
}
