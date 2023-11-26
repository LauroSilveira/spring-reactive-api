package com.lauro.correia.reactive.api.service.post;

import com.lauro.correia.reactive.api.config.WebClientResponseSpec;
import com.lauro.correia.reactive.api.exception.ServerErrorException;
import com.lauro.correia.reactive.api.exception.post.PostNotFoundException;
import com.lauro.correia.reactive.api.mapper.PostCommentsMapperImpl;
import com.lauro.correia.reactive.api.model.Comments;
import com.lauro.correia.reactive.api.model.Post;
import com.lauro.correia.reactive.api.utils.JsonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class PostServiceImplTest extends JsonUtils {

    @SpyBean
    private PostServiceImpl postService;

    @MockBean
    private WebClient webClient;

    @SpyBean
    private PostCommentsMapperImpl postCommentsMapper;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpecMock;

    @Mock
    private WebClient.RequestBodySpec requestBodySpecMock;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpecMock;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;

    @MockBean
    private WebClientResponseSpec responseSpecMock;

    @Test
    void get_post_info_test() {

        //Given
        Post[] jsonResponseExpected = parseToJavaObject(getJsonFile("post/response_getPostsByUserId_1.json"), Post[].class);

        when(this.webClient.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri("/users/{id}/posts", "1")).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.getStatus()).thenReturn(HttpStatus.OK);
        when(responseSpecMock.onStatus(Mockito.any(Predicate.class), Mockito.any(Function.class))).thenCallRealMethod();
        when(responseSpecMock.bodyToFlux(Post.class)).thenReturn(Flux.just(jsonResponseExpected));

        //When
        var posts = this.postService.getPosts("1").block();

        //Then
        assertThat(posts).isNotNull();
        assertThat(posts).size().isEqualTo(Arrays.stream(jsonResponseExpected).toList().size());
        assertThat(posts).usingRecursiveComparison().isEqualTo(Arrays.stream(jsonResponseExpected).toList());
    }

    @Test
    void get_post_comments_by_user_test() {
        //Given
        final var jsonResponseExpected = parseToJavaObject(getJsonFile("post/response_getPostCommentsByUserId_1.json"), Comments[].class);

        when(this.webClient.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri("/posts/{userId}/comments", "1")).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.getStatus()).thenReturn(HttpStatus.OK);
        when(responseSpecMock.onStatus(Mockito.any(Predicate.class), Mockito.any(Function.class))).thenCallRealMethod();
        when(responseSpecMock.bodyToFlux(Comments.class)).thenReturn(Flux.fromIterable(Arrays.stream(jsonResponseExpected).toList()));

        //When
        var commentsVO = this.postService.getPostCommentsByUser("1").collectList().block();

        //Then
        assertThat(commentsVO).isNotNull();
        assertThat(commentsVO).size().isEqualTo(Arrays.stream(jsonResponseExpected).toList().size());
        assertThat(commentsVO).usingRecursiveComparison().isEqualTo(Arrays.stream(jsonResponseExpected)
                .map(c -> this.postCommentsMapper.mapToCommentVO(c)).toList());
    }

    @Test
    void get_post_comments_by_user_should_return_httpStatus_5xx() {
        //Given
        when(this.webClient.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri("/posts/{userId}/comments", "1")).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.getStatus()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);
        when(responseSpecMock.onStatus(Mockito.any(Predicate.class), Mockito.any(Function.class))).thenCallRealMethod();

        //When
        Assertions.assertThrows(ServerErrorException.class, () ->
                this.postService.getPostCommentsByUser("1").collectList().block());
    }

    @Test
    void get_post_comments_by_user_should_return_httpStatus_4xx() {
        //Given
        when(this.webClient.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri("/posts/{userId}/comments", "1")).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.getStatus()).thenReturn(HttpStatus.NOT_FOUND);
        when(responseSpecMock.onStatus(Mockito.any(Predicate.class), Mockito.any(Function.class))).thenCallRealMethod();

        //When
        Assertions.assertThrows(PostNotFoundException.class, () ->
                this.postService.getPostCommentsByUser("1").collectList().block());
    }
}