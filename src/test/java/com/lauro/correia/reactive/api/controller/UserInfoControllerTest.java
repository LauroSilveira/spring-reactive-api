package com.lauro.correia.reactive.api.controller;


import com.lauro.correia.reactive.api.UsersApiController;
import com.lauro.correia.reactive.api.UsersApiDelegate;
import com.lauro.correia.reactive.api.exception.CustomMessageApiError;
import com.lauro.correia.reactive.api.exception.ServerErrorException;
import com.lauro.correia.reactive.api.exception.user.UserNotFoundException;
import com.lauro.correia.reactive.api.service.user.UserService;
import com.lauro.correia.reactive.api.utils.JsonUtils;
import com.lauro.correia.reactive.model.CustomMessageApiErrorDto;
import com.lauro.correia.reactive.model.UserDto;
import com.lauro.correia.reactive.model.UserInfoDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = {UsersApiDelegate.class, UsersApiController.class})
class UserInfoControllerTest extends JsonUtils {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private UserService userService;

    @Test
    @DisplayName("Return Complete User Info for userId 7")
    void should_return_user_info_OK_for_id_7_test() {
        //Given
        final var userInfoDtoJsonExpected = parseToJavaObject(getJsonFile("user/response_get_user_info_id_7.json"), UserInfoDto.class);

        when(this.userService.getUserInfoComplete(anyLong()))
                .thenReturn(Flux.just(userInfoDtoJsonExpected));

        //When
        final var responseBody = this.webTestClient.get()
                .uri("/user/{id}", 7)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserInfoDto.class)
                .returnResult()
                .getResponseBody();

        //Then
        assertNotNull(responseBody);
        assertFalse(responseBody.isEmpty());

        final var userInfoDto = responseBody.stream().findFirst().get();

        assertEquals(userInfoDtoJsonExpected.getId(), userInfoDto.getId());
        assertEquals(userInfoDtoJsonExpected.getAlbums().size(), userInfoDto.getAlbums().size());
        assertEquals(userInfoDtoJsonExpected.getPosts().size(), userInfoDto.getPosts().size());
    }

    @Test
    @DisplayName("Should return User Not Found")
    void should_return_not_found_test() {
        //Given
        when(this.userService.getUserInfoComplete(anyLong()))
                .thenReturn(Flux.error(new UserNotFoundException("User Not Found", CustomMessageApiErrorDto.builder()
                        .msg("User Not Found")
                        .httpStatus(HttpStatus.NOT_FOUND.value())
                        .build())));

        //When
        this.webTestClient.get()
                .uri("/user/{id}", 21)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(CustomMessageApiError.class)
                .value(customApiError -> {
                    assertEquals(HttpStatus.NOT_FOUND.value(), customApiError.getHttpStatus());
                    assertEquals("User Not Found", customApiError.getMsg());
                });
    }

    @Test
    @DisplayName("Return Internal Server Error")
    void should_return_internal_server_error_test() {
        //Given
        when(this.userService.getUserInfoComplete(anyLong()))
                .thenReturn(Flux.error(new ServerErrorException("Internal Server Error", CustomMessageApiErrorDto.builder()
                        .msg("Internal Server Error")
                        .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .build())));

        //When
        this.webTestClient.get()
                .uri("/user/{id}", 21)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody(CustomMessageApiErrorDto.class)
                .value(customApiError -> {
                    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), customApiError.getHttpStatus());
                    assertEquals("Internal Server Error", customApiError.getMsg());
                });
    }

    @Test
    @DisplayName("Return Complete User Info for userId 9")
    void should_return_user_info_OK_for_id_9_test() {
        //Given
        final var userInfoVOJsonExpected = parseToJavaObject(getJsonFile("user/response_get_user_info_id_9.json"), UserInfoDto.class);

        when(this.userService.getUserInfoComplete(anyLong()))
                .thenReturn(Flux.just(userInfoVOJsonExpected));

        //When
        final var responseBody = this.webTestClient.get()
                .uri("/user/{id}", 9)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserInfoDto.class)
                .returnResult()
                .getResponseBody();

        //Then
        assertNotNull(responseBody);
        assertFalse(responseBody.isEmpty());

        final var userInfoVO = responseBody.stream().findFirst().get();

        assertEquals(userInfoVOJsonExpected.getId(), userInfoVO.getId());
        assertEquals(userInfoVOJsonExpected.getAlbums().size(), userInfoVO.getAlbums().size());
        assertEquals(userInfoVOJsonExpected.getPosts().size(), userInfoVO.getPosts().size());
    }

    @Test
    @DisplayName("Return Complete User Info for userId 3")
    void should_return_user_info_OK_for_id_3_test() {
        //Given
        final var userInfoVOJsonExpected = parseToJavaObject(getJsonFile("user/response_get_user_info_id_3.json"), UserInfoDto.class);

        when(this.userService.getUserInfoComplete(anyLong()))
                .thenReturn(Flux.just(userInfoVOJsonExpected));

        //When
        final var responseBody = this.webTestClient.get()
                .uri("/user/{id}", 9)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserInfoDto.class)
                .returnResult()
                .getResponseBody();

        //Then
        assertNotNull(responseBody);
        assertFalse(responseBody.isEmpty());

        final var userInfoVO = responseBody.stream().findFirst().get();

        assertEquals(userInfoVOJsonExpected.getId(), userInfoVO.getId());
        assertEquals(userInfoVOJsonExpected.getAlbums().size(), userInfoVO.getAlbums().size());
        assertEquals(userInfoVOJsonExpected.getPosts().size(), userInfoVO.getPosts().size());
    }

    @Test
    @DisplayName("Return Complete User Info for userId 3")
    void should_return_user_test() {
        //Given
        final var userDto = parseToList(getJsonFile("user/response_get_all_users.json"), UserDto.class);

        when(this.userService.getUsers()).thenReturn(Flux.fromIterable(userDto));

        //When
        final var responseBody = this.webTestClient.get()
                .uri("/user")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserDto.class)
                .returnResult()
                .getResponseBody();

        //Then
        assertNotNull(responseBody);
        assertFalse(responseBody.isEmpty());
        assertEquals(userDto.size(), responseBody.size());
    }

}
