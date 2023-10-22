package com.lauro.correia.reactive.api.exception.post;

import com.lauro.correia.reactive.api.exception.CustomMessageApiError;
import lombok.Getter;

@Getter
public class PostNotFoundException extends RuntimeException {

    private CustomMessageApiError customMessageApiError;
    public PostNotFoundException(String message, CustomMessageApiError customMessageApiError) {
        super(message);
        this.customMessageApiError = customMessageApiError;
    }
}
