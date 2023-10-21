package com.lauro.correia.reactive.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Album(String userId, @JsonProperty("id") String albumId, String title) {
}
