package com.epartner.shop.services;


import com.epartner.shop.converters.UserConverter;
//import com.epartner.shop.domain.Role;
import com.epartner.shop.domain.Role;
import com.epartner.shop.domain.User;
import com.epartner.shop.exceptions.EntityPersist;
import com.epartner.shop.exceptions.NoHashException;
import com.epartner.shop.repositories.MailRepository;
import com.epartner.shop.repositories.RoleRepository;
import com.epartner.shop.repositories.UserRepository;
import com.epartner.shop.representations.UserRepresentation;
import com.epartner.shop.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

/**
 * Created by martin on 05/11/16.
 */
@Service
public class UserService {

    private final UserRepository repository;
    private final UserConverter converter;
    private final PasswordUtil passwordUtil;
    private final MailRepository mailRepository;
    private final RedisService redisService;
    private final RoleRepository roleRepository;

    private static final String USER = "USER";
    private static final String DISABLED = "disabled";
    private static final String AVAILABLE = "available";


    @Autowired
    public UserService(UserRepository repository,
                       UserConverter converter,
                       PasswordUtil passwordUtil,
                       MailRepository mailRepository,
                       RedisService redisService,
                     RoleRepository roleRepository) {
        this.repository = repository;
        this.converter = converter;
        this.passwordUtil = passwordUtil;
        this.mailRepository = mailRepository;
        this.redisService = redisService;
        this.roleRepository = roleRepository;

    }


    public UserRepresentation createUser(UserRepresentation userRepresentation) {
        try {
            this.getByName(userRepresentation.getUsername());
            throw new EntityPersist();
        } catch (EntityNotFoundException e) {
            userRepresentation.setPassword(passwordUtil.encodePassword(userRepresentation.getPassword()));
            userRepresentation.setState(DISABLED);
            User saveUser = this.converter.convert(userRepresentation);
            Role role = roleRepository.findByAuthority(USER);
            saveUser.addRole(role);
            this.repository.save(saveUser);
            this.mailRepository.registerMail(saveUser);
            return this.converter.convert(saveUser);
        }
    }

    public UserRepresentation getByName(String username) {
        return this.repository.findOneByUsername(username)
                .map(it -> this.converter.convert(it))
                .orElseThrow(EntityNotFoundException::new);
    }

    public void accountConfirmation(String hash) {
            String userName = (String) redisService.getValue(hash);
            Optional.ofNullable(userName).orElseThrow(NoHashException::new);
            User user = this.converter.convert(this.getByName(userName));
            user.setState(AVAILABLE);
            this.redisService.deleteValue(hash);
    }

    public void forgot(UserRepresentation userRepresentation) {
        User user = this.converter.convert(this.getByName(userRepresentation.getUsername()));
        if(userRepresentation.getUsername().equals(user.getUsername())
                && userRepresentation.getEmail().equals(user.getEmail())) {
            user.setPassword(this.passwordUtil.generatorPassword());
            this.mailRepository.sendForgotPassword(user);
        }else{
            throw new EntityNotFoundException();
        }
    }

    public Optional<User> authentication(String name, String password)  {
        Optional<User> user = this.repository.findOneByUsername(name);
        User current = user.get();
        if(!user.isPresent()) {
            throw new BadCredentialsException("");
        }
        if(DISABLED.equals(current.getState())) {
            throw new BadCredentialsException("");
        }
        if(current.getUsername().equals(name) && current.getPassword().equals(passwordUtil.encodePassword(password))){
            return user;
        }else {
            throw new BadCredentialsException("Bad Credentials");
        }
    }
}
