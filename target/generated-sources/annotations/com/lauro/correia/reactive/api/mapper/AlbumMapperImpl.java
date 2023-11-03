package com.lauro.correia.reactive.api.mapper;

import com.lauro.correia.reactive.api.model.Album;
import com.lauro.correia.reactive.api.vo.AlbumVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-03T16:40:23+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8 (Homebrew)"
)
@Component
public class AlbumMapperImpl implements AlbumMapper {

    @Override
    public List<AlbumVO> mapToAlbumVO(List<Album> album) {
        if ( album == null ) {
            return null;
        }

        List<AlbumVO> list = new ArrayList<AlbumVO>( album.size() );
        for ( Album album1 : album ) {
            list.add( albumToAlbumVO( album1 ) );
        }

        return list;
    }

    protected AlbumVO albumToAlbumVO(Album album) {
        if ( album == null ) {
            return null;
        }

        String userId = null;
        String albumId = null;
        String title = null;

        userId = album.userId();
        albumId = album.albumId();
        title = album.title();

        AlbumVO albumVO = new AlbumVO( userId, albumId, title );

        return albumVO;
    }
}
