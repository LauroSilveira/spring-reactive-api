package com.lauro.correia.reactive.api.service;

import com.lauro.correia.reactive.api.mapper.UserInfoMapper;
import com.lauro.correia.reactive.api.model.Album;
import com.lauro.correia.reactive.api.model.Post;
import com.lauro.correia.reactive.api.model.UserInfo;
import com.lauro.correia.reactive.api.service.album.AlbumService;
import com.lauro.correia.reactive.api.service.post.PostService;
import com.lauro.correia.reactive.api.service.user.UserService;
import com.lauro.correia.reactive.api.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class RestServicesImpl implements RestService {

    private final WebClient webClient;
    private final UserInfoMapper userInfoMapper;
    private final UserService userService;
    private final AlbumService albumService;
    private final PostService postService;

    public RestServicesImpl(UserInfoMapper userInfoMapper, UserService userService, AlbumService albumService, PostService postService) {
        this.webClient = WebClient.builder().baseUrl("https://jsonplaceholder.typicode.com").build();
        this.userInfoMapper = userInfoMapper;
        this.userService = userService;
        this.albumService = albumService;
        this.postService = postService;
    }

    public Flux<UserInfoVO> callUserServices(String id) {
        var user = this.userService.getUserInfo(id, webClient);
        var post = this.postService.getUserPost(id, webClient);
        var album = this.albumService.getUserAlbum(id, webClient);
        return Flux.zip(user, post, album)
                .map(response -> this.userInfoMapper.mapToUserInfo(response.getT1(), response.getT2(), response.getT3()));
    }
}
