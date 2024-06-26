package com.lauro.correia.reactive.api.exception;

import com.lauro.correia.reactive.model.CustomMessageApiErrorDto;
import lombok.Getter;

@Getter
public class ServerErrorException extends RuntimeException {

    private final CustomMessageApiErrorDto customMessageApiError;

    public ServerErrorException(String message, CustomMessageApiErrorDto customMessageApiError) {
        super(message);
        this.customMessageApiError = customMessageApiError;
    }
}
