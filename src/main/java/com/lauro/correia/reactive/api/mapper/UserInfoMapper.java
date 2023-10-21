package com.lauro.correia.reactive.api.mapper;

import com.lauro.correia.reactive.api.model.Album;
import com.lauro.correia.reactive.api.model.Post;
import com.lauro.correia.reactive.api.model.UserInfo;
import com.lauro.correia.reactive.api.vo.UserInfoVO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CompanyMapper.class, AlbumMapper.class, PostMapper.class, AddressMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserInfoMapper {

    @Mapping(source = "user.userId", target = "userId")
    UserInfoVO mapToUserInfo(UserInfo user, List<Post> post, List<Album> album);
}
