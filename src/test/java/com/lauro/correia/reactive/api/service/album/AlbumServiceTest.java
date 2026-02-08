package com.lauro.correia.reactive.api.service.album;

import com.lauro.correia.reactive.api.config.WebClientResponseSpec;
import com.lauro.correia.reactive.api.exception.ServerErrorException;
import com.lauro.correia.reactive.api.exception.album.AlbumNotFoundException;
import com.lauro.correia.reactive.api.mapper.PostCommentsMapperImpl;
import com.lauro.correia.reactive.api.model.Album;
import com.lauro.correia.reactive.api.utils.JsonUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlbumServiceTest extends JsonUtils {


    @InjectMocks
    private AlbumServiceImpl albumService;

    @Spy
    private WebClient webClient;

    @Spy
    private PostCommentsMapperImpl postCommentsMapper;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpecMock;

    @Mock
    private WebClient.RequestBodySpec requestBodySpecMock;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpecMock;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;

    @Mock
    private WebClientResponseSpec responseSpecMock;

    @Test
    void get_album_info_ok_test() {
        //Given
        final var albumsJson = parseToJavaObject(getJsonFile("album/response_get_albums_for_useId_1.json"), Album[].class);
        when(this.webClient.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri("/users/{id}/albums", 1L)).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.getStatus()).thenReturn(HttpStatus.OK);
        when(responseSpecMock.onStatus(any(Predicate.class), any(Function.class))).thenCallRealMethod();
        when(responseSpecMock.bodyToFlux(Album.class)).thenReturn(Flux.just(albumsJson));

        //When
        final var albums = this.albumService.getUserAlbumById(1L).block();

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
        when(requestHeadersUriSpecMock.uri("/users/{id}/albums", 1L)).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.getStatus()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);
        when(responseSpecMock.onStatus(any(Predicate.class), any(Function.class))).thenCallRealMethod();

        //When
        assertThrows(ServerErrorException.class, () ->
                this.albumService.getUserAlbumById(1L).block());
    }

    @Test
    void get_album_info_should_return_httpStatus_4xx() {
        //Given
        when(this.webClient.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri("/users/{id}/albums", 1L)).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.getStatus()).thenReturn(HttpStatus.NOT_FOUND);
        when(responseSpecMock.onStatus(any(Predicate.class), any(Function.class))).thenCallRealMethod();

        //When
        assertThrows(AlbumNotFoundException.class, () ->
                this.albumService.getUserAlbumById(1L).block());
    }
}
