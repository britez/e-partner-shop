package com.epartner.shop.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Created by martin on 11/11/16.
 */
@Component
public class PasswordUtil {

    public String encodePassword(String pwd) {
        String quotedPassword = "\"" + pwd + "\"";
        char unicodePwd[] = quotedPassword.toCharArray();
        byte pwdArray[] = new byte[unicodePwd.length * 2];

        for (int i = 0; i < unicodePwd.length; i++) {
            pwdArray[i * 2 + 1] = (byte) (unicodePwd[i] >>> 8);
            pwdArray    [i * 2 + 0] = (byte) (unicodePwd[i] & 0xff);

        }

        return new String(pwdArray);
    }


    public String generatorPassword(){
        String SALTCHARS = "ABVWXYZ123456978QRMPCNMTOLK*#";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 32) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

}
