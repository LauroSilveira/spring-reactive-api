package com.lauro.correia.reactive.api.exception.album;

import com.lauro.correia.reactive.api.exception.CustomApiError;
import lombok.Getter;

@Getter
public class AlbumNotFoundException extends RuntimeException {

    private final CustomApiError customApiError;
    public AlbumNotFoundException(String message, CustomApiError customApiError) {
        super(message);
        this.customApiError = customApiError;
    }
}
