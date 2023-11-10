package com.github.golovnyakpa.personal_expenses.dtos.response.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDtoRs {
    private Long id;
    private String username;
    private String email;
}
