package com.lauro.correia.reactive.api.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AlbumVO(String userId, String albumId, String title) {
}
