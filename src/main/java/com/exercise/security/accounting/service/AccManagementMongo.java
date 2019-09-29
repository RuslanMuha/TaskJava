package com.exercise.security.accounting.service;

import java.time.LocalDate;
import java.util.*;

import com.exercise.security.accounting.dao.AccMngRepository;
import com.exercise.security.accounting.entities.AccountMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccManagementMongo implements IAccManagement {

    private AccMngRepository accounts;
    private PasswordEncoder encoder;

    @Autowired
    public AccManagementMongo(AccMngRepository accounts, PasswordEncoder encoder) {
        this.accounts = accounts;
        this.encoder = encoder;
    }

    @Override
    public boolean addAccount(String username, String password,
                              String[] roles) {
        if (accounts.existsById(username))
            return false;
        String epassword = encoder.encode(password);
        Set<String> sroles = new HashSet<>(Arrays.asList(roles));
        AccountMongo account = new AccountMongo(username, epassword, sroles);
        account.setDate(LocalDate.now());
        accounts.save(account);
        return true;
    }

    @Override
    public boolean removeAccount(String username) {
        if (!accounts.existsById(username))
            return false;
        accounts.deleteById(username);
        return true;
    }

    @Override
    public boolean updatePassword(String username, String password) {
        AccountMongo account = accounts.findById(username).orElse(null);
        if (account == null)
            return false;
        if (encoder.matches(password, account.getPassword())) {
            return false;
        }
        account.setPassword(encoder.encode(password));
        account.setDate(LocalDate.now());
        accounts.save(account);
        return true;
    }


}
