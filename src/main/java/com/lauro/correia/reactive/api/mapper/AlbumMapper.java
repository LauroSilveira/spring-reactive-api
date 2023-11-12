package com.lauro.correia.reactive.api.mapper;

import com.lauro.correia.reactive.api.model.Album;
import com.lauro.correia.reactive.api.vo.AlbumVO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AlbumMapper {

    List<AlbumVO> mapToAlbumVO(List<Album> album);
}
