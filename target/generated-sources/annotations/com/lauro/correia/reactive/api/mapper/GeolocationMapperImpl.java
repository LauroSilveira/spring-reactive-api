package com.lauro.correia.reactive.api.mapper;

import com.lauro.correia.reactive.api.model.Geolocation;
import com.lauro.correia.reactive.api.vo.GeolocationVO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-03T16:40:23+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8 (Homebrew)"
)
@Component
public class GeolocationMapperImpl implements GeolocationMapper {

    @Override
    public GeolocationVO mapToGeoLocationVO(Geolocation geo) {
        if ( geo == null ) {
            return null;
        }

        String latitude = null;
        String longetude = null;

        latitude = geo.lat();
        longetude = geo.lng();

        GeolocationVO geolocationVO = new GeolocationVO( latitude, longetude );

        return geolocationVO;
    }
}
