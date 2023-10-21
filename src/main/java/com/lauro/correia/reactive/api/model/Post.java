package com.lauro.correia.reactive.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Post(String userId, @JsonProperty("id") String postId, String title, String body) {
}
