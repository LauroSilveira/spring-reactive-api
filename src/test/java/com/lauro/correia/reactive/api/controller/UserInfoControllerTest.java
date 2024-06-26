package com.lauro.correia.reactive.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lauro.correia.reactive.api.exception.CustomMessageApiError;
import com.lauro.correia.reactive.api.exception.ServerErrorException;
import com.lauro.correia.reactive.api.exception.user.UserNotFoundException;
import com.lauro.correia.reactive.api.service.user.UserService;
import com.lauro.correia.reactive.api.vo.UserInfoVO;
import com.lauro.correia.reactive.api.vo.UserVO;
import com.lauro.correia.reactive.model.UserDto;
import com.lauro.correia.reactive.model.UserInfoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.ResourceUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = UserInfoController.class)
class UserInfoControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserService userService;

    private static ObjectMapper mapper;

    @BeforeEach
    void setup() {
        mapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Return Complete User Info for userId 7")
    void should_return_user_info_OK_for_id_7_test() throws IOException {
        //Given
        final var userInfoVOJsonExpected = mapper.readValue(getJsonFile("response_get_user_info_id_7.json"),
                new TypeReference<UserInfoDto>() {
        });

        when(this.userService.getUserInfoComplete(anyString()))
                .thenReturn(Flux.just(userInfoVOJsonExpected));

        //When
        List<UserInfoVO> responseBody = this.webTestClient.get()
                .uri("/user/{id}", 7)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserInfoVO.class)
                .returnResult()
                .getResponseBody();

        //Then
        assertNotNull(responseBody);
        assertFalse(responseBody.isEmpty());

        final var userInfoVO = responseBody.stream().findFirst().get();

        assertEquals(userInfoVOJsonExpected.getUserId(), userInfoVO.userId());
        assertEquals(userInfoVOJsonExpected.getAlbums().size(), userInfoVO.album().size());
        assertEquals(userInfoVOJsonExpected.getPosts().size(), userInfoVO.post().size());
    }

    @Test
    @DisplayName("Should return User Not Found")
    void should_return_not_found_test() {
        //Given
        when(this.userService.getUserInfoComplete(anyString()))
                .thenReturn(Flux.error(new UserNotFoundException("User Not Found", CustomMessageApiError.builder()
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
        when(this.userService.getUserInfoComplete(anyString()))
                .thenReturn(Flux.error(new ServerErrorException("Internal Server Error", CustomMessageApiError.builder()
                        .msg("Internal Server Error")
                        .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .build())));

        //When
        this.webTestClient.get()
                .uri("/user/{id}", 21)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody(CustomMessageApiError.class)
                .value(customApiError -> {
                    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), customApiError.getHttpStatus());
                    assertEquals("Internal Server Error", customApiError.getMsg());
                });
    }

    @Test
    @DisplayName("Return Complete User Info for userId 9")
    void should_return_user_info_OK_for_id_9_test() throws IOException {
        //Given
        final var userInfoVOJsonExpected = mapper.readValue(getJsonFile("response_get_user_info_id_9.json"),
                new TypeReference<UserInfoDto>() {
        });

        when(this.userService.getUserInfoComplete(anyString()))
                .thenReturn(Flux.just(userInfoVOJsonExpected));

        //When
        List<UserInfoVO> responseBody = this.webTestClient.get()
                .uri("/user/{id}", 9)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserInfoVO.class)
                .returnResult()
                .getResponseBody();

        //Then
        assertNotNull(responseBody);
        assertFalse(responseBody.isEmpty());

        final var userInfoVO = responseBody.stream().findFirst().get();

        assertEquals(userInfoVOJsonExpected.getUserId(), userInfoVO.userId());
        assertEquals(userInfoVOJsonExpected.getAlbums().size(), userInfoVO.album().size());
        assertEquals(userInfoVOJsonExpected.getPosts().size(), userInfoVO.post().size());
    }

    @Test
    @DisplayName("Return Complete User Info for userId 3")
    void should_return_user_info_OK_for_id_3_test() throws IOException {
        //Given
        final var userInfoVOJsonExpected = mapper.readValue(getJsonFile("response_get_user_info_id_3.json"),
                new TypeReference<UserInfoDto>() {
        });

        when(this.userService.getUserInfoComplete(anyString()))
                .thenReturn(Flux.just(userInfoVOJsonExpected));

        //When
        List<UserInfoVO> responseBody = this.webTestClient.get()
                .uri("/user/{id}", 9)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserInfoVO.class)
                .returnResult()
                .getResponseBody();

        //Then
        assertNotNull(responseBody);
        assertFalse(responseBody.isEmpty());

        final var userInfoVO = responseBody.stream().findFirst().get();

        assertEquals(userInfoVOJsonExpected.getUserId(), userInfoVO.userId());
        assertEquals(userInfoVOJsonExpected.getAlbums().size(), userInfoVO.album().size());
        assertEquals(userInfoVOJsonExpected.getPosts().size(), userInfoVO.post().size());
    }

    @Test
    @DisplayName("Return Complete User Info for userId 3")
    void should_return_user_test() throws IOException {
        //Given
        final var usersVO = mapper.readValue(getJsonFile("response_get_all_users.json"), new TypeReference<List<UserDto>>() {
        });

        when(this.userService.getUsers()).thenReturn(Flux.just(usersVO.get(0)));

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

        org.assertj.core.api.Assertions.assertThat(responseBody.stream().findFirst().get())
                .usingRecursiveComparison()
                .isEqualTo(usersVO.getFirst());
    }

    private static File getJsonFile(String jsonName) {
        try {
            return ResourceUtils.getFile("src/test/resources/json/user/" + jsonName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("JSON File not found");
        }
    }
}
