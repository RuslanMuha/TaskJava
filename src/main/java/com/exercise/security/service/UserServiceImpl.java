package com.exercise.security.service;


import com.exercise.security.config.TokenHandler;
import com.exercise.security.entity.Role;
import com.exercise.security.entity.UserCustom;
import com.exercise.security.exceptions.AuthenticationCustomException;
import com.exercise.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.exercise.security.ErrorMessageUsersConstant.*;


@Service
public class UserServiceImpl implements UserService {




    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenHandler tokenHandler;


    @Override
    public String signin(UserCustom user) {

        try {
            List<Role> authorities = userRepository.findByUsername(user.getUsername()).get().getAuthorities();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            return tokenHandler.generateAccessToken(user.getUsername(), authorities, LocalDateTime.now().plusHours(1));
        } catch (Exception e) {
            throw new AuthenticationCustomException(HttpStatus.UNAUTHORIZED.value(), INVALID_USERNAME_OR_PASSWORD);
        }


    }

    @Override
    public void signup(UserCustom user) {
        if (!userRepository.existsByUsername(user.getUsername())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setEnabled(true);
            user.setAccountNonExpired(true);
            user.setCredentialsNonExpired(true);
            user.setAccountNonLocked(true);
            userRepository.save(user);
        } else {
            throw new AuthenticationCustomException(HttpStatus.IM_USED.value(), USER_IN_USE);
        }
    }

    public void deleteUser(String email) {
        userRepository.deleteByUsername(email);
    }


    public String refreshToken(String email) {
        UserCustom user = userRepository.findByUsername(email).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
        return tokenHandler.generateAccessToken(email, user.getAuthorities(), LocalDateTime.now().plusHours(1));
    }
}
