package com.pycogroup.todoapp.config;


import com.pycogroup.todoapp.model.User;
import com.pycogroup.todoapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

//@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    protected UserRepository userRepository;

    protected PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .anyRequest()
                .authenticated().and()
                .formLogin().and()
                .httpBasic();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        User root = userRepository.findByUserName("root");
        if (root == null) {
            root = createUser("root", "root");
        }
        User user = userRepository.findByUserName("user");
        if (user == null) {
            user = createUser("user", "user");
        }
        auth.inMemoryAuthentication().withUser(root.getUserName()).password(root.getPassword()).roles("USER");
        auth.inMemoryAuthentication().withUser(user.getUserName()).password(user.getPassword()).roles("USER");
    }

    private User createUser(String userName, String password) {
        return userRepository.save(User.builder()
                .userName(userName)
                .password(encoder.encode(password))
                .createdAt(LocalDateTime.now())
                .build());
    }
}
