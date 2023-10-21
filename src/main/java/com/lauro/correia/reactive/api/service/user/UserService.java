package com.lauro.correia.reactive.api.service.user;

import com.lauro.correia.reactive.api.model.UserInfo;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public interface UserService {
    Flux<UserInfo> getUserInfo(String id, WebClient webClient);
}
