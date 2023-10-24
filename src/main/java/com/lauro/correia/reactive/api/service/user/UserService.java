package com.lauro.correia.reactive.api.service.user;

import com.lauro.correia.reactive.api.model.UserInfo;
import com.lauro.correia.reactive.api.vo.UserInfoVO;
import com.lauro.correia.reactive.api.vo.UserVO;
import reactor.core.publisher.Flux;

public interface UserService {

    Flux<UserInfoVO> getUserInfoComplete(String id);
    Flux<UserInfo> getUserInfo(String id);
    Flux<UserVO> getUsers();
}
