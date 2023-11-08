package com.github.golovnyakpa.personal_expenses.utils.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthUtils {
    public String getCurrentUsername() {
        var user = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Current user is " + user);
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
