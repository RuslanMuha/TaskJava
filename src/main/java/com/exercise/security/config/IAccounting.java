package com.exercise.security.config;

public interface IAccounting {
String getPassword(String username);
String[]getRoles(String username);
}
