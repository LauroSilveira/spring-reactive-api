package com.lauro.correia.reactive.api.controller;


import com.lauro.correia.reactive.api.UsersApiDelegate;
import com.lauro.correia.reactive.api.service.user.UserService;
import com.lauro.correia.reactive.model.UserDto;
import com.lauro.correia.reactive.model.UserInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserInfoController implements UsersApiDelegate {

    private final UserService userService;

    @Override
    public Flux<UserInfoDto> getUserInfo(Long id, ServerWebExchange exchange) {
        log.info("[UserInfoController] getUserInfo by Id: [{}]", id);
        return this.userService.getUserInfoComplete(id);
    }

    @Override
    public Flux<UserDto> getUsers(ServerWebExchange exchange) {
        log.info("[UserInfoController] getUsers");
        return this.userService.getUsers();
    }

}
