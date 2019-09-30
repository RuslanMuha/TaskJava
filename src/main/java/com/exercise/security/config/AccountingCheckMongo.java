package com.exercise.security.config;

import java.time.LocalDate;

import com.exercise.security.accounting.dao.AccMngRepository;
import com.exercise.security.accounting.entities.AccountMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

@Service
@ManagedResource
public class AccountingCheckMongo implements IAccounting {
    @Value("${period:30}")
    private int expirationPeriod;

    @ManagedAttribute
    public int getExpirationPeriod() {
        return expirationPeriod;
    }

    @ManagedAttribute
    public void setExpirationPeriod(int experationPeriod) {
        this.expirationPeriod = experationPeriod;
    }

    @Autowired
    private AccMngRepository accounts;

    @Override
    public String getPassword(String username) {
        AccountMongo account = accounts.findById(username).orElse(null);
        if (account == null)
            return "";
        LocalDate expDate = account.getDate().plusDays(expirationPeriod);
        if (LocalDate.now().isAfter(expDate) || LocalDate.now().equals(expDate))
            return "";
        return account.getPassword();
    }

    @Override
    public String[] getRoles(String username) {
        AccountMongo account = accounts.findById(username).orElse(null);
        if (account == null)
            return new String[0];
        return account.getRoles().toArray(new String[0]);
    }

}
