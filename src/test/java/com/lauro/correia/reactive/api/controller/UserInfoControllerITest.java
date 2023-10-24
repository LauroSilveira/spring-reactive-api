package com.lauro.correia.reactive.api.controller;

import com.lauro.correia.reactive.api.exception.CustomMessageApiError;
import com.lauro.correia.reactive.api.vo.UserInfoVO;
import com.lauro.correia.reactive.api.vo.UserVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserInfoControllerITest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void get_user_info_response_ok_test() {
        final var responseBody = this.webTestClient.get()
                .uri("/user/{id}", 9)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserInfoVO.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(responseBody);
        assertFalse(responseBody.isEmpty());

        //Since the response of userInfoController is a FLux of one element we can use .stream.findFirst()
        var userInfoVO = responseBody.stream().findFirst().get();

        assertNotNull(userInfoVO);
        assertEquals("9", userInfoVO.userId());
        assertNotNull(userInfoVO.company());
        assertNotNull(userInfoVO.post());
        assertNotNull(userInfoVO.album());
    }

    @Test
    void get_user_info_response_not_found_test() {
       this.webTestClient.get()
                .uri("/user/{id}", 14)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(CustomMessageApiError.class)
               .value(customApiError -> {
                  assertEquals(HttpStatus.NOT_FOUND, customApiError.getHttpStatus());
                  assertEquals("User Not Found", customApiError.getMsg());
               });
    }


    @Test
    void get_user_list_response_OK_test() {
        final var responseBody = this.webTestClient.get()
                .uri("/user")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserVO.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(responseBody);
        assertFalse(responseBody.isEmpty());
        assertEquals(10, responseBody.size());
    }
}