package com.lauro.correia.reactive.api.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@Builder
public class CustomMessageApiError {

    private HttpStatus httpStatus;
    private String msg;
}
