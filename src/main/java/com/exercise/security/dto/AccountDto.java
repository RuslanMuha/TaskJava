package com.exercise.security.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AccountDto {

    private String username;
    private String password;
    private String[] roles;

}
