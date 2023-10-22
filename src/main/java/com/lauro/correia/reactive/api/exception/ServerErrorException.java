package com.lauro.correia.reactive.api.exception;

import lombok.Getter;

@Getter
public class ServerErrorException extends RuntimeException {

    private final CustomMessageApiError customMessageApiError;
    public ServerErrorException(String message, CustomMessageApiError customMessageApiError) {
        super(message);
        this.customMessageApiError = customMessageApiError;
    }
}
