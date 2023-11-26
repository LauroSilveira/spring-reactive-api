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
import com.lauro.correia.reactive.api.vo.UserInfoVO;
import com.lauro.correia.reactive.api.vo.UserVO;
import org.assertj.core.api.Assertions;
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
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserServiceTest extends JsonUtils {

    @MockBean
    private UserInfoMapper userInfoMapper;

    @MockBean
    private AlbumService albumService;

    @MockBean
    private PostService postService;

    @SpyBean
    private UserServiceImpl userService;

    @MockBean
    private WebClient webClient;

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
    void get_user_info_complete_test() {
        //Given
        final var posts = parseToJavaObject(getJsonFile("post/response_getPostsByUserId_1.json"), Post[].class);
        when(this.postService.getPosts(anyString())).thenReturn(Mono.just(Arrays.stream(posts).toList()));

        final var albums = parseToJavaObject(getJsonFile("album/response_getAlbumByUserId_1.json"), Album[].class);
        when(this.albumService.getAlbumInfo(anyString())).thenReturn(Mono.just(Arrays.stream(albums).toList()));

        final var userInfo = parseToJavaObject(getJsonFile("user/response_getUserInfo_id_3.json"), UserInfo.class);
        when(webClient.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri("/users/{id}", "3")).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.getStatus()).thenReturn(HttpStatus.OK);
        when(responseSpecMock.onStatus(Mockito.any(Predicate.class), Mockito.any(Function.class))).thenCallRealMethod();
        when(responseSpecMock.bodyToFlux(UserInfo.class)).thenReturn(Flux.just(userInfo));

        final var userInfoComplete = parseToJavaObject(getJsonFile("user/response_getUserInfoComplete_id_3.json"), UserInfoVO.class);
        when(this.userInfoMapper.mapToUserInfo(any(), anyList(), anyList())).thenReturn(userInfoComplete);

        //When
        final var userInfoVO = this.userService.getUserInfoComplete("3").blockLast();

        //Then
        assertNotNull(userInfoVO);
        assertNotNull(userInfoVO.company());
        assertFalse(userInfoVO.album().isEmpty());
        assertFalse(userInfoVO.post().isEmpty());
        assertEquals("3", userInfoVO.userId());
    }

    @Test
    void get_user_info_test() {
        //Given
        final var userInfoJson = parseToJavaObject(getJsonFile("user/response_getUserInfo_id_3.json"), UserInfo.class);
        when(webClient.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri("/users/{id}", "3")).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.getStatus()).thenReturn(HttpStatus.OK);
        when(responseSpecMock.onStatus(Mockito.any(Predicate.class), Mockito.any(Function.class))).thenCallRealMethod();
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
        when(responseSpecMock.onStatus(Mockito.any(Predicate.class), Mockito.any(Function.class))).thenCallRealMethod();
        when(this.responseSpecMock.bodyToFlux(User.class)).thenReturn(Flux.just(usersJson));
        when(this.userInfoMapper.mapToUser(any(User.class))).thenReturn(new UserVO("3","Clementine Bauch", "Samantha", "Nathan@yesenia.net"));

        //When
        final var users = this.userService.getUsers().blockLast();

        //Then
        assertNotNull(users);
        Assertions.assertThat(users)
                .usingRecursiveComparison()
                .isEqualTo(userInfoMapper.mapToUser(Arrays.stream(usersJson).toList().get(2)));
    }
}