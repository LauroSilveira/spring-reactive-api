package com.lauro.correia.reactive.api.service;

import com.lauro.correia.reactive.api.vo.UserInfoVO;
import reactor.core.publisher.Flux;

public interface RestService {

    Flux<UserInfoVO> callUserServices(String id);
}
