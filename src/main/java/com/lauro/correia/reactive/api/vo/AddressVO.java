package com.lauro.correia.reactive.api.vo;

public record AddressVO(String street, String suite, String city, String zipcode, GeolocationVO geolocation) {
}
