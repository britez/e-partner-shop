package com.epartner.shop.resources;

import com.epartner.shop.representations.RoleRepresentation;
import com.epartner.shop.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by martin on 27/12/16.
 */
@RequestMapping("/role")
@RestController
public class RoleResource {

    private RoleService service;

    @Autowired
    public RoleResource(RoleService service){
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createRole(@RequestBody RoleRepresentation roleRepresentation){
        this.service.create(roleRepresentation);
    }


}
