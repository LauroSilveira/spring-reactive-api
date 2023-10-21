package com.lauro.correia.reactive.api.mapper;

import com.lauro.correia.reactive.api.model.Post;
import com.lauro.correia.reactive.api.vo.PostVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    List<PostVO> maptToVo(List<Post> post);
}
