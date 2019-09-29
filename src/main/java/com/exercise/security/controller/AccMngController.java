package com.exercise.security.controller;

import com.exercise.security.ApiAccountConstant;
import com.exercise.security.accounting.service.IAccManagement;
import com.exercise.security.dto.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.exercise.security.ApiAccountConstant.ACCOUNT;
import static com.exercise.security.ApiAccountConstant.REMOVE_ACCOUNT;
import static com.exercise.security.ApiAccountConstant.UPDATE_PASSWORD;


@RestController
public class AccMngController {
    @Autowired
    IAccManagement accounts;

    @PostMapping(ACCOUNT)
    boolean addAccount(@RequestBody AccountDto account) {
        return accounts.addAccount(account.getUsername(),
                account.getPassword(), account.getRoles());
    }

    @DeleteMapping(REMOVE_ACCOUNT)
    boolean removeAccount(@PathVariable String username) {
        return accounts.removeAccount(username);
    }

    @PutMapping(UPDATE_PASSWORD)
    boolean updatePassword(@PathVariable String username, @PathVariable String password) {
        return accounts.updatePassword(username, password);
    }

}
