package com.github.golovnyakpa.personal_expenses.dtos.request.auth;

import lombok.Data;

@Data
public class RegistrationUserDtoRq {
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
}
