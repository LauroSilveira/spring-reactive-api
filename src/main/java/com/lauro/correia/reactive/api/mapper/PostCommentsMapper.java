package com.lauro.correia.reactive.api.mapper;

import com.lauro.correia.reactive.api.model.Comments;
import com.lauro.correia.reactive.api.vo.CommentsVO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PostCommentsMapper {
    CommentsVO mapToCommentVO(Comments response);

}
