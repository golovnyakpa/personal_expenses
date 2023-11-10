package com.github.golovnyakpa.personal_expenses.exceptions;

public class ResourceForbiddenException extends RuntimeException {
    public ResourceForbiddenException(String message) {
        super(message);
    }
}
