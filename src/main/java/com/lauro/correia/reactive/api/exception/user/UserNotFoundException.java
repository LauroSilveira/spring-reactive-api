package com.lauro.correia.reactive.api.exception.user;

import com.lauro.correia.reactive.api.exception.CustomMessageApiError;
import com.lauro.correia.reactive.model.CustomMessageApiErrorDto;
import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

    private final CustomMessageApiErrorDto customMessageApiError;
    public UserNotFoundException(String message, CustomMessageApiErrorDto customMessageApiError) {
        super(message);
        this.customMessageApiError = customMessageApiError;
    }
}
