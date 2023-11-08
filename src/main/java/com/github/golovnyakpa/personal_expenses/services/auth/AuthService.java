package com.github.golovnyakpa.personal_expenses.services.auth;

import com.github.golovnyakpa.personal_expenses.dao.entities.auth.User;
import com.github.golovnyakpa.personal_expenses.dtos.request.auth.AuthRq;
import com.github.golovnyakpa.personal_expenses.dtos.request.auth.RegistrationUserDtoRq;
import com.github.golovnyakpa.personal_expenses.dtos.response.auth.JwtRs;
import com.github.golovnyakpa.personal_expenses.dtos.response.auth.UserDtoRs;
import com.github.golovnyakpa.personal_expenses.utils.auth.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> createAuthToken(@RequestBody AuthRq authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(), authRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtRs(token));
    }

    // todo add message of error
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDtoRq registrationUserDto) {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (userService.findByUsername(registrationUserDto.getUsername()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = userService.createNewUser(registrationUserDto);
        return ResponseEntity.ok(new UserDtoRs(user.getId(), user.getUsername(), user.getEmail()));
    }
}
