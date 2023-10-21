package com.lauro.correia.reactive.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserInfo(@JsonProperty("id") String userId, String name, String email, Address address, String phone, String website, Company company) {
}
