package com.lauro.correia.reactive.api.controller;

import com.lauro.correia.reactive.api.exception.CustomApiError;
import com.lauro.correia.reactive.api.exception.ServerErrorException;
import com.lauro.correia.reactive.api.exception.album.AlbumNotFoundException;
import com.lauro.correia.reactive.api.exception.post.PostNotFoundException;
import com.lauro.correia.reactive.api.exception.user.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestControllerAdvice(assignableTypes = UserInfoController.class)
public class UserInfoRestControllerAdvice {

    @ExceptionHandler({AlbumNotFoundException.class, PostNotFoundException.class, UserNotFoundException.class})
    public Mono<ResponseEntity<CustomApiError>> handleNotFoundException(final UserNotFoundException e) {
        log.error("[UserInfoRestControllerAdvice] handleNotFoundException");
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(CustomApiError.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .msg(e.getMessage())
                .build()));
    }


    @ExceptionHandler({ServerErrorException.class})
    public Mono<ResponseEntity<CustomApiError>> handleInternalServerException(final ServerErrorException ex) {
        log.error("[UserInfoRestControllerAdvice] handleInternalServerException");
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CustomApiError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .msg(ex.getMessage())
                .build()));
    }

}

