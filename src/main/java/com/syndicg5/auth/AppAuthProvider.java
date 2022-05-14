package com.syndicg5.auth;

import com.syndicg5.service.impl.SyndicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AppAuthProvider extends DaoAuthenticationProvider {
    @Autowired
    SyndicServiceImpl syndicService;

    @Override
    public Authentication authenticate(Authentication authentication) {
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
        String name = auth.getName();
        String password = auth.getCredentials().toString();
        System.out.println(name+" "+password);
        UserDetails user = syndicService.loadUserByUsername(name);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (user == null) {
            throw new BadCredentialsException("Email/Password does  not match for " + auth.getPrincipal());
        }
        if(encoder.matches(password, user.getPassword()))
            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        else
            throw new BadCredentialsException("Email/Password does not match for " + auth.getPrincipal());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
