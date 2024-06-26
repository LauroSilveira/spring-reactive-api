package com.lauro.correia.reactive.api.mapper;

import com.lauro.correia.reactive.api.model.Post;
import com.lauro.correia.reactive.model.PostDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    List<PostDto> mapToPostDto(List<Post> post);
}
