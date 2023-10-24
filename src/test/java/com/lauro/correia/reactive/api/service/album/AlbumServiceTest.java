package com.lauro.correia.reactive.api.service.album;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lauro.correia.reactive.api.model.Album;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ResourceUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
class AlbumServiceTest {


    @SpyBean
    private AlbumServiceImpl albumService;

    @Test
    void get_album_info_ok_test() {
        //When
        final var albums = this.albumService.getAlbumInfo("1", WebClient.builder().baseUrl("https://jsonplaceholder.typicode.com")
                .build()).block();

        //Then
        assertNotNull(albums);
        assertFalse(albums.isEmpty());
        Assertions.assertThat(albums)
                .usingRecursiveComparison()
                .isEqualTo(this.getAlbumsJSON());

    }

    private List<Album> getAlbumsJSON() {
        try {
            return new ObjectMapper().readValue(getJsonFile(), new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Error converting JSON to Jjava Object", e);
        }
    }

    private static File getJsonFile() {
        try {
            return ResourceUtils.getFile("src/test/resources/json/album/response_get_albums_for_useId_1.json");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("JSON File not found");
        }
    }
}
