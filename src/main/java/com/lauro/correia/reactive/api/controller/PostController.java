package com.lauro.correia.reactive.api.controller;


import com.lauro.correia.reactive.api.service.post.PostService;
import com.lauro.correia.reactive.api.vo.CommentsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


@Slf4j
@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<CommentsVO> getPostsCommentsByUser(@PathVariable("userId") final String userId) {
        log.info("[UserInfoController] getUsers");
        return this.postService.getPostCommentsByUser(userId);
    }
}
