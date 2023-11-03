package com.lauro.correia.reactive.api.mapper;

import com.lauro.correia.reactive.api.model.Post;
import com.lauro.correia.reactive.api.vo.PostVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-03T18:38:50+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8 (Homebrew)"
)
@Component
public class PostMapperImpl implements PostMapper {

    @Override
    public List<PostVO> maptToVo(List<Post> post) {
        if ( post == null ) {
            return null;
        }

        List<PostVO> list = new ArrayList<PostVO>( post.size() );
        for ( Post post1 : post ) {
            list.add( postToPostVO( post1 ) );
        }

        return list;
    }

    protected PostVO postToPostVO(Post post) {
        if ( post == null ) {
            return null;
        }

        String userId = null;
        String postId = null;
        String title = null;
        String body = null;

        userId = post.userId();
        postId = post.postId();
        title = post.title();
        body = post.body();

        PostVO postVO = new PostVO( userId, postId, title, body );

        return postVO;
    }
}
