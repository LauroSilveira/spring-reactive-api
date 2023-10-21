package com.lauro.correia.reactive.api.exception.user;

import com.lauro.correia.reactive.api.exception.CustomApiError;
import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

    private final CustomApiError customApiError;
    public UserNotFoundException(String message, CustomApiError customApiError) {
        super(message);
        this.customApiError = customApiError;
    }
}
