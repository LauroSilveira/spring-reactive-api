package com.lauro.correia.reactive.api.service.post;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lauro.correia.reactive.api.model.Post;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ResourceUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class PostServiceImplTest {

    @SpyBean
    private PostServiceImpl postService;

    @Test
    void getPostInfo() {

        //When
        var posts = this.postService.getPostInfo("1", WebClient.builder()
                        .baseUrl("https://jsonplaceholder.typicode.com")
                .build()).block();

        //Then
        assertThat(posts).isNotNull();
        assertThat(posts).size().isEqualTo(this.getPost().size());
        assertThat(posts).usingRecursiveComparison().isEqualTo(this.getPost());
    }

    private List<Post> getPost() {
        try {
            return new ObjectMapper().readValue(getJsonFile(), new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Error converting JSON to Java Object", e);
        }
    }

    private static File getJsonFile() {
        try {
            return ResourceUtils.getFile("src/test/resources/json/post/response_get_posts_for_userId_1.json");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("JSON File not found");
        }
    }
}