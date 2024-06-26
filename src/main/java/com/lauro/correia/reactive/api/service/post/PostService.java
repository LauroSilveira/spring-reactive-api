package com.lauro.correia.reactive.api.service.post;

import com.lauro.correia.reactive.api.model.Post;

import com.lauro.correia.reactive.model.CommentDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface PostService {
    Mono<List<Post>>  getPosts(String id);

    Flux<CommentDto> getPostCommentsByUser(String userId);
}
