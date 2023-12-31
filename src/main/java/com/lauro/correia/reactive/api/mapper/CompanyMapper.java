package com.lauro.correia.reactive.api.mapper;

import com.lauro.correia.reactive.api.model.Company;
import com.lauro.correia.reactive.api.vo.CompanyVO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CompanyMapper {
    CompanyVO maptoCompanyVO(Company company);
}
