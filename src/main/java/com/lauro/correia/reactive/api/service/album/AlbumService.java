package com.lauro.correia.reactive.api.service.album;

import com.lauro.correia.reactive.api.model.Album;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AlbumService {

    Mono<List<Album>> getAlbums(String id);
}
