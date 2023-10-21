package com.lauro.correia.reactive.api.exception;

import lombok.Getter;

@Getter
public class ServerErrorException extends RuntimeException {

    private final CustomApiError customApiError;
    public ServerErrorException(String message, CustomApiError customApiError) {
        super(message);
        this.customApiError = customApiError;
    }
}
