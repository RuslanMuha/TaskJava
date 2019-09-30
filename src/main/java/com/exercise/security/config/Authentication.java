package com.exercise.security.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static com.exercise.security.accounting.ErrorAccountMessage.USER_NOT_FOUND;

@Configuration
public class Authentication implements UserDetailsService {

    @Autowired
    private IAccounting accounts;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String password = accounts.getPassword(username);
        if (password.isEmpty()) {
            throw new UsernameNotFoundException(USER_NOT_FOUND);

        }
        return new User(username, password,
                AuthorityUtils.createAuthorityList(accounts.getRoles(username)));
    }

}
