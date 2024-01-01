package com.ll.medium.domain.post.post.repository;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.post.post.dto.PostListItemDto;
import com.ll.medium.domain.post.post.entity.Post;
import com.ll.medium.standard.base.KwTypeV1;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepositoryCustom {
    <T extends PostListItemDto> Page<T> findByKw(KwTypeV1 kwType, String kw, Member author, Boolean published, Pageable pageable, Class<T> type);
}
