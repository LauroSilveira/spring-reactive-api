package com.lauro.correia.reactive.api.mapper;

import com.lauro.correia.reactive.api.model.Geolocation;
import com.lauro.correia.reactive.api.vo.GeolocationVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GeolocationMapper {

    @Mapping(source = "lat", target = "latitude")
    @Mapping(source = "lng", target = "longetude")
    GeolocationVO mapToGeoLocationVO(Geolocation geo);
}
