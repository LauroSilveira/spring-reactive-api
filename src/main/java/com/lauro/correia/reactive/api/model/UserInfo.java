package com.lauro.correia.reactive.api.model;

public record UserInfo(
        String id,
        String name,
        String username,
        String email,
        Address address,
        String phone,
        String website,
        Company company) {
}
