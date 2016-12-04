package com.epartner.shop.controller;


import org.springframework.security.authentication.DisabledException;
import org.springframework.security.web.WebAttributes;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Created by biandra on 16/09/15.
 */
@RestController
public class UserController {

    /*public static final String ID = "/confirm/{hash}" ;
    private final UserService userService;*/

  /*  @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }*/


    @RequestMapping(value = "/oauth/user")
    @ResponseBody
    public Principal user(Principal user) {
        return user;
    }

    @RequestMapping(value = "/login")
    public ModelAndView login(@RequestParam(value = "error", defaultValue = "false") Boolean error,
                              Model model,
                              HttpServletRequest request) {
        Object isDisabled = request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        if(isDisabled != null && isDisabled instanceof DisabledException){
            model.addAttribute("blocked", true);
            request.getSession().removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        } else {
            model.addAttribute("error", error);
        }
        return new ModelAndView("login", model.asMap());
    }

    @RequestMapping(value = "/formUser")
    public ModelAndView signUp(@RequestParam(value = "error", defaultValue = "false") Boolean error,
                              Model model,
                              HttpServletRequest request) {
        Object isDisabled = request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        if(isDisabled != null && isDisabled instanceof DisabledException){
            model.addAttribute("blocked", true);
            request.getSession().removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        } else {
            model.addAttribute("error", error);
        }
        return new ModelAndView("formUser", model.asMap());
    }

    @RequestMapping(value = "/confirmation")
    public ModelAndView confirmation(@RequestParam(value = "error", defaultValue = "false") Boolean error,
                               Model model,
                               HttpServletRequest request) {
        Object isDisabled = request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        if(isDisabled != null && isDisabled instanceof DisabledException){
            model.addAttribute("blocked", true);
            request.getSession().removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        } else {
            model.addAttribute("error", error);
        }
        return new ModelAndView("formUser", model.asMap());
    }




    /*@RequestMapping(method = RequestMethod.POST,value = "/sing-up")
    public void create(@RequestBody UserRepresentation userRepresentation){
        userService.createUser(userRepresentation);
    }

    @RequestMapping(value = ID, method = RequestMethod.PUT )
    public void accountConfirmation(@PathVariable String hash , @RequestBody UserRepresentation userRepresentation){
        userService.accountConfirmation(hash,userRepresentation);
    }*/




}