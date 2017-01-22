package com.epartner.shop.repositories;

import com.epartner.shop.domain.User;
import com.epartner.shop.exceptions.MailCantBeSendException;
import com.epartner.shop.services.RedisService;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Repository;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by martin on 11/11/16.
 */
@Repository
public class MailRepoImpl implements MailRepository {
    
    @Value("${partner-properties.logo}")
    private String URLBANNER;
    
    private RedisService redisService;
    private VelocityEngine velocityEngine;
    private JavaMailSender mailSender;

    private static final String CHARSET_UTF8 = "UTF-8";
    private static final String SUBJECT = "Nuevo Usuario";


    @Autowired
    public MailRepoImpl(RedisService redisService,
                        VelocityEngine velocityEngine,
                        JavaMailSender mailSender) {
        this.redisService = redisService;
        this.velocityEngine = velocityEngine;
        this.mailSender = mailSender;
    }


    @Value("${mail.link}")
    private String LINK;


    private String generateHash(){
        return UUID.randomUUID().toString();
    }

    @Override
    public void registerMail(User user) {
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(user.getEmail());
            message.setSubject(SUBJECT);
            Map model = new HashMap();
            String hash = generateHash();
            model.put("name",user.getUsername());
            model.put("pass",user.getPassword());
            model.put("link", LINK + hash );
            model.put("logoBanner", URLBANNER);
            String text = VelocityEngineUtils.mergeTemplateIntoString(
                    velocityEngine, "/templates/registerMail.vm",CHARSET_UTF8, model);
            redisService.setValue(hash,user.getUsername());
            message.setText(text, true);
        };
        this.mailSender.send(preparator);
    }

    @Override
    public void sendForgotPassword(User user) {
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(user.getEmail());
            message.setSubject(SUBJECT);
            Map model = new HashMap();
            model.put("name",user.getUsername());
            model.put("pass",user.getPassword());
            String text = VelocityEngineUtils.mergeTemplateIntoString(
                    velocityEngine, "/templates/forgotpassMail.vm",CHARSET_UTF8, model);
            message.setText(text, true);
        };
        this.mailSender.send(preparator);
    }

}

