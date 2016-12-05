package com.epartner.shop.services;


import com.epartner.shop.converters.UserConverter;
import com.epartner.shop.domain.User;
import com.epartner.shop.exceptions.EntityPersist;
import com.epartner.shop.exceptions.NoHashException;
import com.epartner.shop.repositories.MailRepository;
import com.epartner.shop.repositories.UserRepository;
import com.epartner.shop.representations.UserRepresentation;
import com.epartner.shop.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final String DISABLED = "disabled";
    private static final String AVAILABLE = "available";


    @Autowired
    public UserService(UserRepository repository,
                       UserConverter converter,
                       PasswordUtil passwordUtil,
                       MailRepository mailRepository, RedisService redisService) {
        this.repository = repository;
        this.converter = converter;
        this.passwordUtil = passwordUtil;
        this.mailRepository = mailRepository;
        this.redisService = redisService;
    }


    public UserRepresentation createUser(UserRepresentation userRepresentation) {
        try {
            this.getByName(userRepresentation.getUsername());
            throw new EntityPersist();
        } catch (EntityNotFoundException e) {
            userRepresentation.setPassword(passwordUtil.encodePassword(userRepresentation.getPassword()));
            userRepresentation.setState(DISABLED);
            User saveUser = this.converter.convert(userRepresentation);
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
}
