package com.lauro.correia.reactive.api.service.user;

import com.lauro.correia.reactive.api.mapper.UserInfoMapperImpl;
import com.lauro.correia.reactive.api.model.*;
import com.lauro.correia.reactive.api.service.album.AlbumService;
import com.lauro.correia.reactive.api.service.post.PostService;
import com.lauro.correia.reactive.api.vo.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @MockBean
    private UserInfoMapperImpl userInfoMapper;

    @MockBean
    private AlbumService albumService;

    @MockBean
    private PostService postService;

    @SpyBean
    private UserServiceImpl userService;

    @MockBean
    private WebClient webClient;

    @Test
    void get_user_info_complete_test() {
        //Given
        when(this.userService.getUserInfoRest(anyString()))
                .thenReturn(Flux.just(buildUserInfo()));
        when(this.postService.getPostInfo(anyString(), any()))
                .thenReturn(Mono.just(List.of(buildPostInfo())));
        when(this.albumService.getAlbumInfo(anyString(), any()))
                .thenReturn(Mono.just(List.of(buildAlbumInfo())));
        when(this.userInfoMapper.mapToUserInfo(any(), anyList(), anyList()))
                .thenReturn(buildUserInfoVO());
        //When
        final var userInfoVO = this.userService.getUserInfoComplete("3").blockLast();

        //Then
        assertNotNull(userInfoVO);
        assertNotNull(userInfoVO.company());
        assertFalse(userInfoVO.album().isEmpty());
        assertFalse(userInfoVO.post().isEmpty());
        assertEquals("3", userInfoVO.userId());
    }

    private UserInfo buildUserInfo() {
        return new UserInfo("3", "Clementine Bauch", "Nathan@yesenia.net",
                new Address("Douglas Extension", "Suite 847", "McKenziehaven", "59590-4157",
                        new Geolocation("-68.6102", "-47.0653")), "1-463-123-4447", "ramiro.info",
                new Company("Romaguera-Jacobson", "", "Face to face bifurcated interface"));
    }

    private Post buildPostInfo() {
        return new Post("3", "21", "asperiores ea ipsam voluptatibus modi minima quia sint", "repellat aliquid praesentium dolorem quo\\nsed totam minus non itaque\\nnihil labore molestiae sunt dolor eveniet hic recusandae veniam\\ntempora et tenetur expedita sunt");
    }

    private Album buildAlbumInfo() {
        return new Album("3", "21", "epudiandae voluptatem optio est consequatur rem in temporibus et");
    }

    private UserInfoVO buildUserInfoVO() {
        return new UserInfoVO("3", "Clementine Bauch", "Nathan@yesenia.net",
                new AddressVO("Douglas Extension", "Suite 847", "McKenziehaven", "59590-4157",
                        new GeolocationVO("-68.6102", "-47.0653")), "1-463-123-4447", "ramiro.info",
                new CompanyVO("Romaguera-Jacobson", "", "Face to face bifurcated interface"),
                List.of(new AlbumVO("3", "21", "epudiandae voluptatem optio est consequatur rem in temporibus et")),
                List.of(new PostVO("3", "21", "asperiores ea ipsam voluptatibus modi minima quia sint", "repellat aliquid praesentium dolorem quo\\nsed totam minus non itaque\\nnihil labore molestiae sunt dolor eveniet hic recusandae veniam\\ntempora et tenetur expedita sunt"))
        );
    }
}