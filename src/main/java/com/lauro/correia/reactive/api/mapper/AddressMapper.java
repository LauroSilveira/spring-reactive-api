package com.lauro.correia.reactive.api.mapper;

import com.lauro.correia.reactive.api.model.Address;
import com.lauro.correia.reactive.api.vo.AddressVO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {GeolocationMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AddressMapper {

    @Mapping(source = "geo", target = "geolocation")
    AddressVO mapToAddressVo(Address address);
}
