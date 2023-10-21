package com.lauro.correia.reactive.api.model;

public record Address(String street, String suite, String city, String zipcode, Geolocation geo) {
}
