package com.lauro.correia.reactive.api.service.album;

import com.lauro.correia.reactive.api.exception.CustomMessageApiError;
import com.lauro.correia.reactive.api.exception.ServerErrorException;
import com.lauro.correia.reactive.api.exception.album.AlbumNotFoundException;
import com.lauro.correia.reactive.api.model.Album;
import com.lauro.correia.reactive.api.service.user.UserServiceImpl;
import com.lauro.correia.reactive.model.CustomMessageApiErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class AlbumServiceImpl implements AlbumService {

    private final WebClient webClient;

    public AlbumServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }


    @Override
    public Mono<List<Album>> getAlbums(String id) {
        log.info("[UserServiceImpl] - Getting UserAlbum for id: [{}]", id);
        return webClient.get()
                .uri("/users/{id}/albums", id)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, this::handleServerError)
                .onStatus(HttpStatusCode::is4xxClientError, this::handleClientError)
                .bodyToFlux(Album.class)
                .doOnNext(userInfo -> log.info("Received UserAlbum response: [{}]", userInfo))
                .collectList();
    }

    private Mono<? extends Throwable> handleClientError(ClientResponse response) {
        return Mono.error(new AlbumNotFoundException("Album Not Found", CustomMessageApiError.builder().build()));
    }

    private Mono<? extends Throwable> handleServerError(final ClientResponse response) {
        return Mono.error(new ServerErrorException("Internal Server error", CustomMessageApiErrorDto.builder().build()));
    }
}
