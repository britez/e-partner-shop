package com.epartner.shop.Resources;


import com.epartner.shop.representations.UserRepresentation;
import com.epartner.shop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.epartner.shop.Resources.UserResource.USERS;

/**
 * Created by martin on 05/11/16.
 */
@RequestMapping(value = USERS)
@RestController
public class UserResource {

    public static final String ID = "/confirm/{hash}" ;
    public static final String USERS = "api/user";
    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody UserRepresentation userRepresentation){
        userService.createUser(userRepresentation);
    }

    @RequestMapping(value = ID, method = RequestMethod.PUT )
    public void accountConfirmation(@PathVariable String hash , @RequestBody UserRepresentation userRepresentation){
        userService.accountConfirmation(hash,userRepresentation);
    }




}
