package com.lauro.correia.reactive.api.controller;

import com.lauro.correia.reactive.api.exception.ServerErrorException;
import com.lauro.correia.reactive.api.exception.album.AlbumNotFoundException;
import com.lauro.correia.reactive.api.exception.post.PostNotFoundException;
import com.lauro.correia.reactive.api.exception.user.UserNotFoundException;
import com.lauro.correia.reactive.model.CustomMessageApiErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@Slf4j
@RestControllerAdvice
public class UserInfoRestControllerAdvice {

    @ExceptionHandler({AlbumNotFoundException.class, PostNotFoundException.class, UserNotFoundException.class})
    public Mono<ResponseEntity<CustomMessageApiErrorDto>> handleNotFoundException(final UserNotFoundException e) {
        log.error("[UserInfoRestControllerAdvice] handleNotFoundException");
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(CustomMessageApiErrorDto.builder()
                .httpStatus(HttpStatus.NOT_FOUND.value())
                .msg(e.getMessage())
                .build()));
    }


    @ExceptionHandler({ServerErrorException.class})
    public Mono<ResponseEntity<CustomMessageApiErrorDto>> handleInternalServerException(final ServerErrorException ex) {
        log.error("[UserInfoRestControllerAdvice] handleInternalServerException");
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CustomMessageApiErrorDto.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .msg(ex.getMessage())
                .build()));
    }
}

