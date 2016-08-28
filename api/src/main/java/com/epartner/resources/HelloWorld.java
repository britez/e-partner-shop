package com.epartner.resources;

import com.epartner.representation.Hello;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by martin on 14/07/16.
 */
@RestController
public class HelloWorld {


    @RequestMapping(value = "/hello")
    public Hello sayHello() {
        Hello result = new Hello();
        result.setMessage("Hello world!");
        return result;
    }

}
