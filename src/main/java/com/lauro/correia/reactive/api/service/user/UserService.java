package com.lauro.correia.reactive.api.service.user;

import com.lauro.correia.reactive.api.model.UserInfo;
import com.lauro.correia.reactive.model.UserDto;
import com.lauro.correia.reactive.model.UserInfoDto;
import reactor.core.publisher.Flux;

public interface UserService {

    Flux<UserInfoDto> getUserInfoComplete(String id);
    Flux<UserInfo> getUserInfo(String id);
    Flux<UserDto> getUsers();
}
