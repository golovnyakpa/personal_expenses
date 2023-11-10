package com.github.golovnyakpa.personal_expenses.dtos.response.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtRs {
    private String token;
}
