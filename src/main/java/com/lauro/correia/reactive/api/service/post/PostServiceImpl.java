package com.lauro.correia.reactive.api.service.post;

import com.lauro.correia.reactive.api.exception.CustomMessageApiError;
import com.lauro.correia.reactive.api.exception.ServerErrorException;
import com.lauro.correia.reactive.api.exception.post.PostNotFoundException;
import com.lauro.correia.reactive.api.mapper.PostCommentsMapper;
import com.lauro.correia.reactive.api.mapper.PostMapper;
import com.lauro.correia.reactive.api.model.Comments;
import com.lauro.correia.reactive.api.model.Post;
import com.lauro.correia.reactive.model.CommentDto;
import com.lauro.correia.reactive.model.CustomMessageApiErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

    private final WebClient webClient;
    private final PostCommentsMapper postCommentsMapper;

    public PostServiceImpl(WebClient webclient, PostCommentsMapper postCommentsMapper) {
        this.webClient = webclient;
        this.postCommentsMapper = postCommentsMapper;
    }

    @Override
    public Mono<List<Post>> getPosts(String id) {
        log.info("[UserServiceImpl] - Getting UserPost for id: [{}]", id);
        return this.webClient
                .get()
                .uri("/users/{id}/posts", id)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, this::handleServerError)
                .onStatus(HttpStatusCode::is4xxClientError, this::handleClientError)
                .bodyToFlux(Post.class)
                .doOnNext(userInfo -> log.info("Received UserPost response: [{}]", userInfo))
                .collectList();
    }

    @Override
    public Flux<CommentDto> getPostCommentsByUser(String userId) {
        log.info("[UserServiceImpl] - Getting Post Comments by UserId: [{}]", userId);
        return this.webClient
                .get()
                .uri("/posts/{userId}/comments", userId)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, this::handleServerError)
                .onStatus(HttpStatusCode::is4xxClientError, this::handleClientError)
                .bodyToFlux(Comments.class)
                .doOnNext(comments -> log.info("Received Post Comments by UserId: [{}]", comments))
                .map(this.postCommentsMapper::mapToCommentDto);
    }

    private Mono<? extends Throwable> handleClientError(ClientResponse response) {
        return Mono.error(new PostNotFoundException("Post Not Found", CustomMessageApiError.builder().build()));
    }

    private Mono<? extends Throwable> handleServerError(final ClientResponse response) {
        return Mono.error(new ServerErrorException("Internal Server error", CustomMessageApiErrorDto.builder().build()));
    }
}
