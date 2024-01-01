package com.ll.medium.domain.post.post.service;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.post.post.dto.PostListItemDto;
import com.ll.medium.domain.post.post.dto.PostListItemDtoImpl;
import com.ll.medium.domain.post.post.entity.Post;
import com.ll.medium.domain.post.post.entity.PostConfig;
import com.ll.medium.domain.post.post.repository.PostRepository;
import com.ll.medium.global.globalMapper.GlobalMapper;
import com.ll.medium.global.rsData.RsData.RsData;
import com.ll.medium.standard.base.KwTypeV1;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final GlobalMapper globalMapper;

    @Transactional
    public void write(Member author, String title, String pureBody, boolean published) {
        String body = genNewConfigStr(title, published, pureBody);

        Post post = Post.builder()
                .author(author)
                .title(title)
                .body(body)
                .published(published)
                .build();

        postRepository.save(post);
    }

    private String genNewConfigStr(String title, boolean published, String body) {
        return new PostConfig(title, published, body).getBody();
    }

    public Object findTop30ByPublishedOrderByIdDesc(boolean isPublished) {
        return postRepository.findTop30ByPublishedOrderByIdDesc(isPublished);
    }

    public Optional<Post> findById(long id) {
        return postRepository.findById(id);
    }

    public Page<Post> search(String kw, Pageable pageable) {
        return postRepository.findByTitleContainingIgnoreCaseOrBodyContainingIgnoreCase(kw, kw, pageable);
    }

    public <T> List<T> findByPublished(boolean published, Class<T> type) {
        return postRepository.findByPublishedOrderByIdDesc(published, type);
    }

    public <T> List<T> findByAuthor(Member author, Class<T> type) {
        return postRepository.findByAuthorOrderByIdDesc(author, type);
    }

    public <T> Optional<T> findById(long id, Class<T> type) {
        return postRepository.findById(id, type);
    }

    @Transactional
    public <T> RsData<T> findTempOrMake(Member author, Class<T> type) {
        Optional<T> opPost = postRepository.findTop1ByAuthorAndPublishedAndTitleOrderByIdDesc(
                author,
                false,
                "임시글",
                type
        );

        return RsData.of(
                "200",
                "임시글 가져오기 성공",
                opPost.orElseGet(() -> {
                    String title = "임시글";
                    boolean published = false;
                    String body = "";
                    body = genNewConfigStr(title, published, body);

                    Post post = Post.builder()
                            .author(author)
                            .title(title)
                            .body(body)
                            .published(published)
                            .build();

                    postRepository.save(post);

                    return globalMapper.map(post, type);
                })
        );
    }

    public Optional<Post> findLatest() {
        return postRepository.findTop1ByOrderByIdDesc();
    }

    public boolean canEdit(Member actor, Post post) {
        return post.getAuthor().equals(actor);
    }

    @Transactional
    public void edit(Post post, String body) {
        PostConfig postConfig = new PostConfig(body);
        post.setTitle(postConfig.getTitle());
        post.setPublished(postConfig.isOpen());
        post.setBody(postConfig.getBody());
    }

    public <T extends PostListItemDto> Page<T> findByKw(KwTypeV1 kwType, String kw, Member author, Boolean published, Pageable pageable, Class<T> type) {
        return postRepository.findByKw(kwType, kw, author, published, pageable, type);
    }
}
