package com.lauro.correia.reactive.api.mapper;

import com.lauro.correia.reactive.api.model.Company;
import com.lauro.correia.reactive.model.CompanyDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CompanyDto maptoCompanyDto(Company company);
}
