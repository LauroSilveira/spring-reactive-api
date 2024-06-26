package com.lauro.correia.reactive.api.service.user;

import com.lauro.correia.reactive.api.config.WebClientResponseSpec;
import com.lauro.correia.reactive.api.mapper.UserInfoMapper;
import com.lauro.correia.reactive.api.model.Album;
import com.lauro.correia.reactive.api.model.Post;
import com.lauro.correia.reactive.api.model.User;
import com.lauro.correia.reactive.api.model.UserInfo;
import com.lauro.correia.reactive.api.service.album.AlbumService;
import com.lauro.correia.reactive.api.service.post.PostService;
import com.lauro.correia.reactive.api.utils.JsonUtils;
import com.lauro.correia.reactive.model.UserDto;
import com.lauro.correia.reactive.model.UserInfoDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest extends JsonUtils {

    @Mock
    private UserInfoMapper userInfoMapper;

    @Mock
    private AlbumService albumService;

    @Mock
    private PostService postService;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpecMock;

    @Mock
    private WebClient.RequestBodySpec requestBodySpecMock;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpecMock;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;

    @Mock
    private WebClientResponseSpec responseSpecMock;


    @Test
    void get_user_info_complete_test() {
        //Given
        final var posts = parseToList(getJsonFile("post/response_getPostsByUserId_1.json"), Post.class);
        final var albums = parseToList(getJsonFile("album/response_getAlbumByUserId_1.json"), Album.class);
        final var userInfo = parseToJavaObject(getJsonFile("user/response_getUserInfo_id_3.json"), UserInfo.class);
        final var userInfoComplete = parseToJavaObject(getJsonFile("user/response_getUserInfoComplete_id_3.json"), UserInfoDto.class);

        when(this.postService.getPosts(anyString())).thenReturn(Mono.just(posts));
        when(this.albumService.getAlbums(anyString())).thenReturn(Mono.just(albums));
        when(webClient.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri("/users/{id}", "3")).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.getStatus()).thenReturn(HttpStatus.OK);
        when(responseSpecMock.onStatus(any(Predicate.class), any(Function.class))).thenCallRealMethod();
        when(responseSpecMock.bodyToFlux(UserInfo.class)).thenReturn(Flux.just(userInfo));
        when(this.userInfoMapper.mapToUserInfoDto(any(), any(), anyList())).thenReturn(userInfoComplete);

        //When
        final var userInfoDto = this.userService.getUserInfoComplete("3").blockLast();

        //Then
        assertNotNull(userInfoDto);
        assertNotNull(userInfoDto.getCompany());
        assertFalse(userInfoDto.getAlbums().isEmpty());
        assertFalse(userInfoDto.getPosts().isEmpty());
        assertEquals("3", userInfoDto.getUserId());
    }

    @Test
    void get_user_info_test() {
        //Given
        final var userInfoJson = parseToJavaObject(getJsonFile("user/response_getUserInfo_id_3.json"), UserInfo.class);
        when(webClient.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri("/users/{id}", "3")).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.getStatus()).thenReturn(HttpStatus.OK);
        when(responseSpecMock.onStatus(any(Predicate.class), any(Function.class))).thenCallRealMethod();
        when(responseSpecMock.bodyToFlux(UserInfo.class)).thenReturn(Flux.just(userInfoJson));

        //When
        final var userInfo = this.userService.getUserInfo("3")
                .blockLast();

        //Then
        assertNotNull(userInfo);
        Assertions.assertThat(userInfo)
                .usingRecursiveComparison()
                .isEqualTo(userInfoJson);
    }

    @Test
    void get_users_list_test() {
        //Given
        final var usersJson = parseToJavaObject(getJsonFile("user/response_getAllUsers.json"), User[].class);
        when(this.webClient.get()).thenReturn(requestHeadersUriSpecMock);
        when(this.requestHeadersUriSpecMock.uri(anyString())).thenReturn(requestHeadersSpecMock);
        when(this.requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.getStatus()).thenReturn(HttpStatus.OK);
        when(responseSpecMock.onStatus(any(Predicate.class), any(Function.class))).thenCallRealMethod();
        when(this.responseSpecMock.bodyToFlux(User.class)).thenReturn(Flux.just(usersJson));
        when(this.userInfoMapper.mapToUserDto(any(User.class))).thenReturn(new UserDto("3", "Clementine Bauch", "Clementine", "Nathan@yesenia.net"));

        //When
        final var users = this.userService.getUsers().blockLast();

        //Then
        assertNotNull(users);
        Assertions.assertThat(users)
                .usingRecursiveComparison()
                .isEqualTo(userInfoMapper.mapToUserDto(Arrays.stream(usersJson).toList().get(2)));
    }
}