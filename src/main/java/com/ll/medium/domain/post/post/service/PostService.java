package com.ll.medium.domain.post.post.service;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.post.post.entity.Post;
import com.ll.medium.domain.post.post.repository.PostRepository;
import com.ll.medium.global.globalMapper.GlobalMapper;
import com.ll.medium.global.rsData.RsData.RsData;
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
    public void write(Member author, String title, String body, boolean isPublished) {
        Post post = Post.builder()
                .author(author)
                .title(title)
                .body(body)
                .published(isPublished)
                .build();

        postRepository.save(post);
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
                    Post post = Post.builder()
                            .author(author)
                            .title("임시글")
                            .body("""
                                    $$config
                                    title: 임시글
                                    open: false
                                    tags: #태그1 #태그2
                                    $$
                                    """.stripIndent().trim())
                            .published(false)
                            .build();

                    postRepository.save(post);

                    return globalMapper.map(post, type);
                })
        );
    }
}
