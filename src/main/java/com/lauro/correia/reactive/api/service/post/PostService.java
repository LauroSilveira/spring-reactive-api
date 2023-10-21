package com.lauro.correia.reactive.api.service.post;

import com.lauro.correia.reactive.api.model.Post;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

public interface PostService {
    Mono<List<Post>>  getUserPost(String id, WebClient webClient);
}
