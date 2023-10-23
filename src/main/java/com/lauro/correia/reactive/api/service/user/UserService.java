package com.lauro.correia.reactive.api.service.user;

import com.lauro.correia.reactive.api.model.UserInfo;
import com.lauro.correia.reactive.api.vo.UserInfoVO;
import reactor.core.publisher.Flux;

public interface UserService {

    Flux<UserInfoVO> getUserInfoComplete(String id);
    Flux<UserInfo> getUserInfoRest(String id);
}
