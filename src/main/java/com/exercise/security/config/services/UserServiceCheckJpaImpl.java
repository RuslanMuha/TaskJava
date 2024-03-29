package com.exercise.security.config.services;


import com.exercise.security.entity.UserCustom;
import com.exercise.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceCheckJpaImpl implements UserServiceCheckJpa {
    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<UserCustom> getUser(String username) {
        return userRepository.findByUsername(username);
    }
}
