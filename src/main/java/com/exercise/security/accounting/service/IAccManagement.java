package com.exercise.security.accounting.service;

public interface IAccManagement {
boolean addAccount(String username, String password, String[] roles);
boolean removeAccount(String username);
boolean updatePassword(String username, String password);
}
