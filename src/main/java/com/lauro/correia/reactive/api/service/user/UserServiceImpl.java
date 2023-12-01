package com.lauro.correia.reactive.api.service.user;

import com.lauro.correia.reactive.api.exception.CustomMessageApiError;
import com.lauro.correia.reactive.api.exception.ServerErrorException;
import com.lauro.correia.reactive.api.exception.user.UserNotFoundException;
import com.lauro.correia.reactive.api.mapper.UserInfoMapper;
import com.lauro.correia.reactive.api.model.User;
import com.lauro.correia.reactive.api.model.UserInfo;
import com.lauro.correia.reactive.api.service.album.AlbumService;
import com.lauro.correia.reactive.api.service.post.PostService;
import com.lauro.correia.reactive.api.vo.UserInfoVO;
import com.lauro.correia.reactive.api.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final WebClient webClient;
    private final UserInfoMapper userInfoMapper;
    private final AlbumService albumService;
    private final PostService postService;

    public UserServiceImpl(WebClient webClient, UserInfoMapper userInfoMapper, AlbumService albumService, PostService postService) {
        this.webClient = webClient;
        this.userInfoMapper = userInfoMapper;
        this.albumService = albumService;
        this.postService = postService;
    }

    @Override
    public Flux<UserInfoVO> getUserInfoComplete(String id) {
        final var user = this.getUserInfo(id);
        final var post = this.postService.getPosts(id);
        final var album = this.albumService.getAlbumInfo(id);
        return Flux.zip(user, post, album)
                .map(response -> this.userInfoMapper.mapToUserInfo(response.getT1(), response.getT2(), response.getT3()))
                .doOnNext(userInfoVO -> log.info("UserInfoVO complete: {}", userInfoVO));
    }


    @Override
    public Flux<UserInfo> getUserInfo(String id) {
        log.info("[UserServiceImpl] - Getting UserInfo for id: [{}]", id);
        return this.webClient
                .get()
                .uri("/users/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, this::handleClientError)
                .onStatus(HttpStatusCode::is5xxServerError, this::handleServerError)
                .bodyToFlux(UserInfo.class)
                .doOnNext(userInfo -> log.info("Received UserInfo response: [{}]", userInfo));
    }

    @Override
    public Flux<UserVO> getUsers() {
        log.info("[UserServiceImpl] - Get All Users");
        return this.webClient.get()
                .uri("/users")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, this::handleClientError)
                .onStatus(HttpStatusCode::is5xxServerError, this::handleServerError)
                .bodyToFlux(User.class)
                .doOnNext(users -> log.info("Received list of Users available: [{}]", users))
                .map(this.userInfoMapper::mapToUser);
    }

    private Mono<? extends Throwable> handleClientError(ClientResponse response) {
        return Mono.error(new UserNotFoundException("User Not Found", CustomMessageApiError.builder().build()));
    }

    private Mono<? extends Throwable> handleServerError(final ClientResponse response) {
        return Mono.error(new ServerErrorException("Internal Server error", CustomMessageApiError.builder().build()));
    }
}
