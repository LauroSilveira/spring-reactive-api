package com.lauro.correia.reactive.api.exception.comment;

import com.lauro.correia.reactive.api.exception.CustomMessageApiError;

public class CommentNotFoundException extends RuntimeException {

    private final CustomMessageApiError customMessageApiError;

    public CommentNotFoundException(String message, CustomMessageApiError customMessageApiError) {
        super(message);
        this.customMessageApiError = customMessageApiError;
    }
}
