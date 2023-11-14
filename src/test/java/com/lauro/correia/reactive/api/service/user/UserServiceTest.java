package com.lauro.correia.reactive.api.service.user;

import com.lauro.correia.reactive.api.mapper.UserInfoMapper;
import com.lauro.correia.reactive.api.model.Address;
import com.lauro.correia.reactive.api.model.Album;
import com.lauro.correia.reactive.api.model.Company;
import com.lauro.correia.reactive.api.model.Geolocation;
import com.lauro.correia.reactive.api.model.Post;
import com.lauro.correia.reactive.api.model.User;
import com.lauro.correia.reactive.api.model.UserInfo;
import com.lauro.correia.reactive.api.service.album.AlbumService;
import com.lauro.correia.reactive.api.service.post.PostService;
import com.lauro.correia.reactive.api.vo.AddressVO;
import com.lauro.correia.reactive.api.vo.AlbumVO;
import com.lauro.correia.reactive.api.vo.CompanyVO;
import com.lauro.correia.reactive.api.vo.GeolocationVO;
import com.lauro.correia.reactive.api.vo.PostVO;
import com.lauro.correia.reactive.api.vo.UserInfoVO;
import com.lauro.correia.reactive.api.vo.UserVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceTest {

    @MockBean
    private UserInfoMapper userInfoMapper;

    @MockBean
    private AlbumService albumService;

    @MockBean
    private PostService postService;

    @SpyBean
    private UserServiceImpl userService;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpecMock;

    @Mock
    private WebClient.RequestBodySpec requestBodySpecMock;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpecMock;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;

    @Mock
    private WebClient.ResponseSpec responseSpecMock;


    @Test
    void get_user_info_complete_test() {
        //Given
        when(this.userService.getUserInfo(anyString()))
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

    @Test
    void get_user_info_test() {
        //Given
        when(webClient.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(anyString())).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToFlux(UserInfo.class)).thenReturn(Flux.just(buildUserInfo()));

        //When
        final var userInfo = this.userService.getUserInfo("3")
                .blockLast();

        //Then
        assertNotNull(userInfo);
        Assertions.assertThat(userInfo)
                .usingRecursiveComparison()
                .isEqualTo(this.buildUserInfo());
    }

    @Test
    void get_users_list_test() {
        //Given
        when(this.webClient.get()).thenReturn(requestHeadersUriSpecMock);
        when(this.requestHeadersUriSpecMock.uri(anyString())).thenReturn(requestHeadersSpecMock);
        when(this.requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(this.responseSpecMock.bodyToFlux(User.class)).thenReturn(Flux.just(this.buildUser()));
        when(this.userInfoMapper.mapToUser(any())).thenReturn(buildUserVO());

        //When
        final var users = this.userService.getUsers()
                .blockLast();

        //Then
        assertNotNull(users);
        Assertions.assertThat(users)
                .usingRecursiveComparison()
                .isEqualTo(this.buildUserVO());
    }



    private UserInfo buildUserInfo() {
        return new UserInfo("3", "Clementine Bauch", "Nathan@yesenia.net",
                new Address("Douglas Extension", "Suite 847", "McKenziehaven", "59590-4157",
                        new Geolocation("-68.6102", "-47.0653")), "1-463-123-4447", "ramiro.info",
                new Company("Romaguera-Jacobson", "Face to face bifurcated interface", "e-enable strategic applications"));
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

    private User buildUser() {
        return new User("1", "Leanne Graham", "Sincere@april.biz");
    }
    private UserVO buildUserVO() {
        return new UserVO("1", "Leanne Graham", "Sincere@april.biz");
    }
}