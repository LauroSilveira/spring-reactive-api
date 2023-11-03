package com.lauro.correia.reactive.api.mapper;

import com.lauro.correia.reactive.api.model.Company;
import com.lauro.correia.reactive.api.vo.CompanyVO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-03T18:54:44+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8 (Homebrew)"
)
@Component
public class CompanyMapperImpl implements CompanyMapper {

    @Override
    public CompanyVO maptoCompanyVO(Company company) {
        if ( company == null ) {
            return null;
        }

        String name = null;
        String catchPhrase = null;
        String bs = null;

        name = company.name();
        catchPhrase = company.catchPhrase();
        bs = company.bs();

        CompanyVO companyVO = new CompanyVO( name, catchPhrase, bs );

        return companyVO;
    }
}
