package com.lauro.correia.reactive.api.controller;


import com.lauro.correia.reactive.api.service.user.UserService;
import com.lauro.correia.reactive.api.vo.UserInfoVO;
import com.lauro.correia.reactive.api.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserInfoController {

    private final UserService userService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<UserInfoVO> getUserInfo(@PathVariable("id") String id) {
        log.info("[UserInfoController] getuserInfo for Id: [{}]", id);
        return this.userService.getUserInfoComplete(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<UserVO> getUsers() {
        log.info("[UserInfoController] getUsers");
        return this.userService.getUsers();
    }
}
