package com.lauro.correia.reactive.api.controller;

import com.lauro.correia.reactive.api.exception.CustomMessageApiError;
import com.lauro.correia.reactive.api.exception.ServerErrorException;
import com.lauro.correia.reactive.api.exception.album.AlbumNotFoundException;
import com.lauro.correia.reactive.api.exception.post.PostNotFoundException;
import com.lauro.correia.reactive.api.exception.user.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@Slf4j
@RestControllerAdvice(assignableTypes = UserInfoController.class)
public class UserInfoRestControllerAdvice {

    @ExceptionHandler({AlbumNotFoundException.class, PostNotFoundException.class, UserNotFoundException.class})
    public Mono<ResponseEntity<CustomMessageApiError>> handleNotFoundException(final UserNotFoundException e) {
        log.error("[UserInfoRestControllerAdvice] handleNotFoundException");
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(CustomMessageApiError.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .msg(e.getMessage())
                .build()));
    }


    @ExceptionHandler({ServerErrorException.class})
    public Mono<ResponseEntity<CustomMessageApiError>> handleInternalServerException(final ServerErrorException ex) {
        log.error("[UserInfoRestControllerAdvice] handleInternalServerException");
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CustomMessageApiError.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .msg(ex.getMessage())
                .build()));
    }
}

