package com.lauro.correia.reactive.api.exception.post;

import com.lauro.correia.reactive.api.exception.CustomApiError;
import lombok.Getter;

@Getter
public class PostNotFoundException extends RuntimeException {

    private CustomApiError customApiError;
    public PostNotFoundException(String message, CustomApiError customApiError) {
        super(message);
        this.customApiError = customApiError;
    }
}
