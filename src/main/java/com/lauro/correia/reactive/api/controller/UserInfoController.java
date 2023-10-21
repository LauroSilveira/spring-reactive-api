package com.lauro.correia.reactive.api.controller;

import com.lauro.correia.reactive.api.vo.UserInfoVO;
import com.lauro.correia.reactive.api.service.RestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
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

    private final RestService restService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<UserInfoVO> getUserInfo(@Validated @PathVariable("id") String id) {
        log.info("[UserInfoController] getuserInfo for ID: [{}]", id);
        return this.restService.callUserServices(id);
    }

}
