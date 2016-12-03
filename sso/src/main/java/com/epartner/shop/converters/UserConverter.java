package com.epartner.shop.converters;

import com.epartner.shop.domain.User;
import com.epartner.shop.representations.UserRepresentation;
import org.springframework.stereotype.Component;

/**
 * Created by martin on 05/11/16.
 */
@Component
public class UserConverter {

    public User convert(UserRepresentation userRepresentation){
        User result = new User();
        result.setUsername(userRepresentation.getUsername());
        result.setEmail(userRepresentation.getEmail());
        result.setId(userRepresentation.getId());
        result.setAddress(userRepresentation.getAddress());
        result.setMobilePhone(userRepresentation.getMobilePhone());
        result.setLastName(userRepresentation.getLastName());
        result.setPhone(userRepresentation.getPhone());
        result.setPassword(userRepresentation.getPassword());
        result.setState(userRepresentation.getState());
        return result;
    }

    public UserRepresentation convert(User user){
        UserRepresentation result = new UserRepresentation();
        result.setUsername(user.getUsername());
        result.setEmail(user.getEmail());
        result.setId(user.getId());
        result.setPhone(user.getPhone());
        result.setLastName(user.getLastName());
        result.setAddress(user.getAddress());
        result.setMobilePhone(user.getMobilePhone());
        result.setPassword(user.getPassword());
        result.setState(user.getState());
        return result;
    }
}
