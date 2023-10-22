package com.lauro.correia.reactive.api.exception.album;

import com.lauro.correia.reactive.api.exception.CustomMessageApiError;
import lombok.Getter;

@Getter
public class AlbumNotFoundException extends RuntimeException {

    private final CustomMessageApiError customMessageApiError;
    public AlbumNotFoundException(String message, CustomMessageApiError customMessageApiError) {
        super(message);
        this.customMessageApiError = customMessageApiError;
    }
}
