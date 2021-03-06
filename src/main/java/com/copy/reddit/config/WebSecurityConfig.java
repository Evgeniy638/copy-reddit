package com.copy.reddit.config;

import com.copy.reddit.model.User;
import com.copy.reddit.service.DetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    final DetailsService detailsService;

    @Autowired
    public WebSecurityConfig(DetailsService detailsService) {
        this.detailsService = detailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(detailsService).passwordEncoder(User.PASSWORD_ENCODER);
    }

    /**
     * по url: "posts/likes/**", "/posts", "/subscription/**"
     * может перйти только авторизованный пользователь
     * и для авторизации используется базовая авторизация
     * и блокировать csrf
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("posts/likes/**", "/posts", "/subscription/**").authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }
}
