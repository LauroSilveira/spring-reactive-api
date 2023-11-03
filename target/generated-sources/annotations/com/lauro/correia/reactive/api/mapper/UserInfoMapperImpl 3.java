package com.lauro.correia.reactive.api.mapper;

import com.lauro.correia.reactive.api.model.Album;
import com.lauro.correia.reactive.api.model.Post;
import com.lauro.correia.reactive.api.model.User;
import com.lauro.correia.reactive.api.model.UserInfo;
import com.lauro.correia.reactive.api.vo.AddressVO;
import com.lauro.correia.reactive.api.vo.AlbumVO;
import com.lauro.correia.reactive.api.vo.CompanyVO;
import com.lauro.correia.reactive.api.vo.PostVO;
import com.lauro.correia.reactive.api.vo.UserInfoVO;
import com.lauro.correia.reactive.api.vo.UserVO;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-03T12:54:20+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8 (Homebrew)"
)
@Component
public class UserInfoMapperImpl implements UserInfoMapper {

    private final CompanyMapper companyMapper;
    private final AlbumMapper albumMapper;
    private final PostMapper postMapper;
    private final AddressMapper addressMapper;

    @Autowired
    public UserInfoMapperImpl(CompanyMapper companyMapper, AlbumMapper albumMapper, PostMapper postMapper, AddressMapper addressMapper) {

        this.companyMapper = companyMapper;
        this.albumMapper = albumMapper;
        this.postMapper = postMapper;
        this.addressMapper = addressMapper;
    }

    @Override
    public UserInfoVO mapToUserInfo(UserInfo user, List<Post> post, List<Album> album) {
        if ( user == null && post == null && album == null ) {
            return null;
        }

        String userId = null;
        String name = null;
        String email = null;
        AddressVO address = null;
        String phone = null;
        String website = null;
        CompanyVO company = null;
        if ( user != null ) {
            userId = user.userId();
            name = user.name();
            email = user.email();
            address = addressMapper.mapToAddressVo( user.address() );
            phone = user.phone();
            website = user.website();
            company = companyMapper.maptoCompanyVO( user.company() );
        }
        List<PostVO> post1 = null;
        post1 = postMapper.maptToVo( post );
        List<AlbumVO> album1 = null;
        album1 = albumMapper.mapToAlbumVO( album );

        UserInfoVO userInfoVO = new UserInfoVO( userId, name, email, address, phone, website, company, album1, post1 );

        return userInfoVO;
    }

    @Override
    public UserVO mapToUser(User response) {
        if ( response == null ) {
            return null;
        }

        String id = null;
        String name = null;
        String email = null;

        id = response.id();
        name = response.name();
        email = response.email();

        UserVO userVO = new UserVO( id, name, email );

        return userVO;
    }
}
