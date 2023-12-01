package com.lauro.correia.reactive.api.service.album;

import com.lauro.correia.reactive.api.config.WebClientResponseSpec;
import com.lauro.correia.reactive.api.exception.ServerErrorException;
import com.lauro.correia.reactive.api.exception.album.AlbumNotFoundException;
import com.lauro.correia.reactive.api.exception.post.PostNotFoundException;
import com.lauro.correia.reactive.api.mapper.PostCommentsMapperImpl;
import com.lauro.correia.reactive.api.model.Album;
import com.lauro.correia.reactive.api.utils.JsonUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AlbumServiceTest extends JsonUtils {


    @SpyBean
    private AlbumServiceImpl albumService;

    @MockBean
    private WebClient webClient;

    @SpyBean
    private PostCommentsMapperImpl postCommentsMapper;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpecMock;

    @Mock
    private WebClient.RequestBodySpec requestBodySpecMock;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpecMock;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;

    @MockBean
    private WebClientResponseSpec responseSpecMock;

    @Test
    void get_album_info_ok_test() {
        //Given
        final var albumsJson = parseToJavaObject(getJsonFile("album/response_get_albums_for_useId_1.json"), Album[].class);
        when(this.webClient.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri("/users/{id}/albums", "1")).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.getStatus()).thenReturn(HttpStatus.OK);
        when(responseSpecMock.onStatus(Mockito.any(Predicate.class), Mockito.any(Function.class))).thenCallRealMethod();
        when(responseSpecMock.bodyToFlux(Album.class)).thenReturn(Flux.just(albumsJson));

        //When
        final var albums = this.albumService.getAlbumInfo("1").block();

        //Then
        assertNotNull(albums);
        assertFalse(albums.isEmpty());
        Assertions.assertThat(albums)
                .usingRecursiveComparison()
                .isEqualTo(Arrays.stream(albumsJson).toList());

    }

    @Test
    void get_album_info_should_return_httpStatus_5xx() {
        //Given
        when(this.webClient.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri("/users/{id}/albums", "1")).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.getStatus()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);
        when(responseSpecMock.onStatus(Mockito.any(Predicate.class), Mockito.any(Function.class))).thenCallRealMethod();

        //When
        org.junit.jupiter.api.Assertions.assertThrows(ServerErrorException.class, () ->
                this.albumService.getAlbumInfo("1").block());
    }

    @Test
    void get_album_info_should_return_httpStatus_4xx() {
        //Given
        when(this.webClient.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri("/users/{id}/albums", "1")).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.getStatus()).thenReturn(HttpStatus.NOT_FOUND);
        when(responseSpecMock.onStatus(Mockito.any(Predicate.class), Mockito.any(Function.class))).thenCallRealMethod();

        //When
        org.junit.jupiter.api.Assertions.assertThrows(AlbumNotFoundException.class, () ->
                this.albumService.getAlbumInfo("1").block());
    }
}
