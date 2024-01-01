package com.ll.medium.domain.post.post.repository;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.post.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    List<Post> findTop30ByPublishedOrderByIdDesc(boolean isPublished);

    Page<Post> findByTitleContainingIgnoreCaseOrBodyContainingIgnoreCase(String kw, String kw_, Pageable pageable);

    <T> List<T> findByPublishedOrderByIdDesc(boolean published, Class<T> type);

    <T> List<T> findByAuthorOrderByIdDesc(Member author, Class<T> type);

    <T> Optional<T> findById(long id, Class<T> type);

    <T> Optional<T> findTop1ByAuthorAndPublishedAndTitleOrderByIdDesc(Member author, boolean published, String title, Class<T> type);

    Optional<Post> findTop1ByOrderByIdDesc();
}
