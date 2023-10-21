package com.lauro.correia.reactive.api.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@Builder
public class CustomApiError {

    private HttpStatus httpStatus;
    private String msg;
}
