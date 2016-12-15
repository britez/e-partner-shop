package com.epartner.shop.resources;


import com.epartner.shop.representations.UserRepresentation;
import com.epartner.shop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.epartner.shop.resources.UserResource.USERS;

/**
 * Created by martin on 05/11/16.
 */
@RequestMapping(value = USERS)
@RestController
public class UserResource {

    public static final String ID = "/forgot" ;
    public static final String USERS = "/user";
    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody UserRepresentation userRepresentation){
        userService.createUser(userRepresentation);
    }

    @RequestMapping(method = RequestMethod.PUT,value = ID)
    public void forgot(@RequestBody UserRepresentation userRepresentation){
        userService.forgot(userRepresentation);
    }



}
