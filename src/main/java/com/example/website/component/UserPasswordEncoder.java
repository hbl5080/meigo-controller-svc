package com.example.website.component;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence){

        return BCrypt.hashpw(charSequence.toString(),BCrypt.gensalt());
    }

    @Override
    public boolean matches(CharSequence charSequence,String s){
        return BCrypt.checkpw(charSequence.toString(),s);
    }
}
