package com.lauro.correia.reactive.api.exception.user;

import com.lauro.correia.reactive.api.exception.CustomMessageApiError;
import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

    private final CustomMessageApiError customMessageApiError;
    public UserNotFoundException(String message, CustomMessageApiError customMessageApiError) {
        super(message);
        this.customMessageApiError = customMessageApiError;
    }
}
