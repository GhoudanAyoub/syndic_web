package com.syndicg5.auth;

import com.syndicg5.service.impl.SyndicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    SyndicServiceImpl syndicService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(syndicService);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(new DeniedAuthenticationEntryPoint())
                .and().authenticationProvider(getProvider())
                .formLogin()
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .successHandler(new AuthentificationLoginSuccessHandler())
                .failureHandler(new UrlAuthenticationFailureHandler())
                .and().logout().logoutUrl("/logout")
                .logoutSuccessHandler(new AuthentificationLogoutSuccessHandler())
                .invalidateHttpSession(true)
                .and()
                .authorizeRequests().antMatchers("/", "/login").permitAll()
                .antMatchers("/logout").permitAll()
                .antMatchers("/syndic/**").authenticated()
                .antMatchers("/").hasAnyRole("USER", "ADMIN")
                // .antMatchers("/api/appartements/**", "/api/categories/**",
                // "/api/depenses/**", "/api/immeubles/**", "/api/residents/**",
                // "/api/revenus/**", "/api/sessions/**").authenticated()
                .antMatchers("/script/**", "/src/**", "/syndic/**", "/vendors/**","/assets/**")
                .permitAll()
                .anyRequest().permitAll();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    public class DeniedAuthenticationEntryPoint extends Http403ForbiddenEntryPoint {
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response,
                AuthenticationException authException) throws IOException {
            // TODO Auto-generated method stub
            response.sendRedirect("/index.html");
        }

    }

    private class UrlAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                AuthenticationException exception) throws IOException, ServletException {
            // TODO Auto-generated method stub
            super.onAuthenticationFailure(request, response, exception);
            response.sendRedirect("/index.html");
        }
    }

    private class AuthentificationLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                Authentication authentication) throws IOException, ServletException {
            response.setStatus(HttpServletResponse.SC_OK);

            response.sendRedirect("/syndic/dash.html");
        }
    }

    private class AuthentificationLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
        @Override
        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                Authentication authentication) throws IOException, ServletException {
            response.setStatus(HttpServletResponse.SC_OK);
            response.sendRedirect("/index.html");
        }
    }

    @Bean
    public AuthenticationProvider getProvider() {
        AppAuthProvider provider = new AppAuthProvider();
        provider.setUserDetailsService(syndicService);
        return provider;
    }
}
