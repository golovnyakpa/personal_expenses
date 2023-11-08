package com.github.golovnyakpa.personal_expenses.controllers.auth;

import com.github.golovnyakpa.personal_expenses.dtos.request.auth.AuthRq;
import com.github.golovnyakpa.personal_expenses.dtos.request.auth.RegistrationUserDtoRq;
import com.github.golovnyakpa.personal_expenses.services.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody AuthRq authRequest) {
        return authService.createAuthToken(authRequest);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDtoRq registrationUserDto) {
        return authService.createNewUser(registrationUserDto);
    }
}
