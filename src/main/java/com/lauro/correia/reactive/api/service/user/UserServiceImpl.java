package com.lauro.correia.reactive.api.service.user;

import com.lauro.correia.reactive.api.exception.CustomApiError;
import com.lauro.correia.reactive.api.exception.ServerErrorException;
import com.lauro.correia.reactive.api.exception.user.UserNotFoundException;
import com.lauro.correia.reactive.api.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Override
    public Flux<UserInfo> getUserInfo(String id, WebClient webClient) {
        log.info("[UserServiceImpl] - Getting UserInfo for id: [{}]", id);
        return webClient.get()
                .uri("/users/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, this::handleClientError)
                .onStatus(HttpStatusCode::is5xxServerError, this::handleServerError)
                .bodyToFlux(UserInfo.class)
                .doOnNext(userInfo -> log.info("Received UserInfo response: [{}]", userInfo));
    }

    private Mono<? extends Throwable> handleClientError(ClientResponse response) {
        return Mono.error(new UserNotFoundException("User Not Found", CustomApiError.builder().build()));
    }

    private Mono<? extends Throwable> handleServerError(final ClientResponse response) {
        return Mono.error(new ServerErrorException("Internal Server error", CustomApiError.builder().build()));
    }
}
