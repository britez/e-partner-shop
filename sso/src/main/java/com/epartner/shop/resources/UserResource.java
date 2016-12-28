package com.epartner.shop.resources;


import com.epartner.shop.exceptions.EntityPersist;
import com.epartner.shop.representations.UserRepresentation;
import com.epartner.shop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView create(@ModelAttribute UserRepresentation userRepresentation, Model model){
        try {
            userService.createUser(userRepresentation);
        } catch (EntityPersist ex) {
            model.addAttribute("error", "El usuario ya existe");
            return new ModelAndView("formUser", model.asMap());
        }
        return new ModelAndView("success");
    }

    @RequestMapping(method = RequestMethod.POST,value = ID)
    public ModelAndView forgot(@ModelAttribute UserRepresentation userRepresentation){
        userService.forgot(userRepresentation);
        return new ModelAndView("changed");
    }



}
