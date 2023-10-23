package com.lauro.correia.reactive.api.service.album;

import com.lauro.correia.reactive.api.model.Album;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AlbumService {

    Mono<List<Album>> getAlbumInfo(String id, WebClient webClient);
}
