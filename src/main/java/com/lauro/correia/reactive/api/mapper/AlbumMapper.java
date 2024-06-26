package com.lauro.correia.reactive.api.mapper;

import com.lauro.correia.reactive.api.model.Album;
import com.lauro.correia.reactive.model.AlbumDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AlbumMapper {

    List<AlbumDto> mapToAlbumDto(List<Album> album);
}
