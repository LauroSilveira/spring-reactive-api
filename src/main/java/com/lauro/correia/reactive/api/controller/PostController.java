package com.lauro.correia.reactive.api.controller;


import com.lauro.correia.reactive.api.PostApiDelegate;
import com.lauro.correia.reactive.api.service.post.PostService;
import com.lauro.correia.reactive.model.CommentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;


@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController implements PostApiDelegate {

    private final PostService postService;

    @Override
    public Flux<CommentDto> getPostsCommentsByUser(final String userId, ServerWebExchange exchange) {
        log.info("[UserInfoController] Get posts comments by user {}", userId);
        return this.postService.getPostCommentsByUser(userId);
    }
}
