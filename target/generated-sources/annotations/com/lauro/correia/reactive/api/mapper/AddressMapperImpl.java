package com.lauro.correia.reactive.api.mapper;

import com.lauro.correia.reactive.api.model.Address;
import com.lauro.correia.reactive.api.vo.AddressVO;
import com.lauro.correia.reactive.api.vo.GeolocationVO;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-25T12:54:25+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8 (Homebrew)"
)
@Component
public class AddressMapperImpl implements AddressMapper {

    @Autowired
    private GeolocationMapper geolocationMapper;

    @Override
    public AddressVO mapToAddressVo(Address address) {
        if ( address == null ) {
            return null;
        }

        GeolocationVO geolocation = null;
        String street = null;
        String suite = null;
        String city = null;
        String zipcode = null;

        geolocation = geolocationMapper.mapToGeoLocationVO( address.geo() );
        street = address.street();
        suite = address.suite();
        city = address.city();
        zipcode = address.zipcode();

        AddressVO addressVO = new AddressVO( street, suite, city, zipcode, geolocation );

        return addressVO;
    }
}
