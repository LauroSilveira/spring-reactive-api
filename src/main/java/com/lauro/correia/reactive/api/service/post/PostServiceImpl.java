package com.lauro.correia.reactive.api.service.post;

import com.lauro.correia.reactive.api.exception.CustomMessageApiError;
import com.lauro.correia.reactive.api.exception.ServerErrorException;
import com.lauro.correia.reactive.api.exception.post.PostNotFoundException;
import com.lauro.correia.reactive.api.model.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class PostServiceImpl implements PostService {
    @Override
    public Mono<List<Post>> getPostInfo(String id, WebClient webClient) {
        log.info("[UserServiceImpl] - Getting UserPost for id: [{}]", id);
        return webClient.get()
                .uri("/users/{id}/posts", id)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, this::handleServerError)
                .onStatus(HttpStatusCode::is4xxClientError, this::handleClientError)
                .bodyToFlux(Post.class)
                .doOnNext(userInfo -> log.info("Received UserPost response: [{}]", userInfo))
                .collectList();
    }

    private Mono<? extends Throwable> handleClientError(ClientResponse response) {
        return Mono.error(new PostNotFoundException("Post Not Found", CustomMessageApiError.builder().build()));
    }

    private Mono<? extends Throwable> handleServerError(final ClientResponse response) {
        return Mono.error(new ServerErrorException("Internal Server error", CustomMessageApiError.builder().build()));
    }
}
