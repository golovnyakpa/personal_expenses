package com.github.golovnyakpa.personal_expenses.dtos.request.auth;

import lombok.Data;

@Data
public class AuthRq {
    private String username;
    private String password;
}
