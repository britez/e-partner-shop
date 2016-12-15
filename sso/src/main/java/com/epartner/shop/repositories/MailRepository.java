package com.epartner.shop.repositories;


import com.epartner.shop.domain.User;
import com.epartner.shop.representations.UserRepresentation;

/**
 * Created by martin on 11/11/16.
 */
public interface MailRepository {


   void registerMail(User user);

    void sendForgotPassword(User user);
}
