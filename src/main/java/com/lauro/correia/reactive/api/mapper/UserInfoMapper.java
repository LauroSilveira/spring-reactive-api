package com.lauro.correia.reactive.api.mapper;

import com.lauro.correia.reactive.api.model.Album;
import com.lauro.correia.reactive.api.model.Post;
import com.lauro.correia.reactive.api.model.User;
import com.lauro.correia.reactive.api.model.UserInfo;
import com.lauro.correia.reactive.model.UserDto;
import com.lauro.correia.reactive.model.UserInfoDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CompanyMapper.class, AlbumMapper.class, PostMapper.class, AddressMapper.class})
public interface UserInfoMapper {

    UserInfoDto mapToUserInfoDto(UserInfo user, List<Post> posts, List<Album> albums);

    UserDto mapToUserDto(User response);
}
