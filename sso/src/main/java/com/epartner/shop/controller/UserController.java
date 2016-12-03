package com.epartner.shop.controller;


import com.epartner.shop.CustomDefaultOAuth2ExceptionRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.web.WebAttributes;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by biandra on 16/09/15.
 */
@RestController
public class UserController {

    private String AUTH_URL;

    @Autowired
    public UserController(@Value("${epartner.oauth.uri}") String authUrl){
        this.AUTH_URL = String.format(CustomDefaultOAuth2ExceptionRenderer.URL_FORMAT, authUrl);
    }

    @RequestMapping(value = "/oauth/user", method = RequestMethod.GET)
    @ResponseBody
    public Principal user(Principal user) {
        return user;
    }

    @RequestMapping(value = "/oauth/settings", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> settings(){
        Map<String, String> result = new HashMap<>();
        result.put("auth_url", this.AUTH_URL);
        return result;
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

}