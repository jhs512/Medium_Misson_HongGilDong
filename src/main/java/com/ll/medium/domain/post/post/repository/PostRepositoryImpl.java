package com.ll.medium.domain.post.post.repository;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.post.post.dto.PostDto;
import com.ll.medium.domain.post.post.dto.PostListItemDto;
import com.ll.medium.domain.post.post.entity.QPost;
import com.ll.medium.global.globalMapper.GlobalMapper;
import com.ll.medium.standard.base.KwTypeV1;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.HashMap;
import java.util.Map;

import static com.ll.medium.domain.post.post.entity.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final GlobalMapper globalMapper;

    @Override
    public <T extends PostListItemDto> Page<T> findByKw(KwTypeV1 kwType, String kw, Member author, Boolean published, Pageable pageable, Class<T> type) {
        BooleanBuilder builder = new BooleanBuilder();

        if (author != null) {
            builder.and(post.author.eq(author));
        }

        if (published != null) {
            builder.and(post.published.eq(published));
        }

        if ( kw != null && !kw.isBlank() ) {
            applyKeywordFilter(kwType, kw, builder);
        }

        JPAQuery<T> postsQuery = createPostsQuery(builder, type);
        applySorting(pageable, postsQuery);

        postsQuery.offset(pageable.getOffset()).limit(pageable.getPageSize());

        JPAQuery<Long> totalQuery = createTotalQuery(builder);

        return PageableExecutionUtils.getPage(postsQuery.fetch(), pageable, totalQuery::fetchOne);
    }

    private void applyKeywordFilter(KwTypeV1 kwType, String kw, BooleanBuilder builder) {
        switch (kwType) {
            case kwType.TITLE -> builder.and(post.title.containsIgnoreCase(kw));
            case kwType.TITLE_OR_BODY -> builder.and(post.body.containsIgnoreCase(kw));
            case kwType.NAME -> builder.and(post.author.username.eq(kw));
            default -> builder.and(
                    post.title.containsIgnoreCase(kw)
                            .or(post.author.username.eq(kw))
            );
        }
    }

    private <T extends PostListItemDto> QBean<T> getProjection(Class<T> type, QPost post) {
        Map<String, Expression<?>> bindings = new HashMap<>();

        // AbsPostDto 인터페이스의 공통 필드 바인딩
        bindings.put("id", post.id);
        bindings.put("createDate", post.createDate);
        bindings.put("modifyDate", post.modifyDate);
        bindings.put("authorId", post.author.id);
        bindings.put("authorUsername", post.author.username);
        bindings.put("title", post.title);
        bindings.put("published", post.published);

        // PostDto 클래스에만 존재하는 추가 필드
        if (PostDto.class.isAssignableFrom(type)) {
            bindings.put("body", post.body);
        }

        return Projections.fields(type, bindings);
    }

    private <T extends PostListItemDto> JPAQuery<T> createPostsQuery(BooleanBuilder builder, Class<T> type) {
        return (JPAQuery<T>) jpaQueryFactory
                .select(
                        Projections.bean(
                                globalMapper.getConcreteType(type),
                                post.id,
                                post.createDate,
                                post.modifyDate,
                                post.author.id.as("authorId"),
                                post.author.username.as("authorUsername"),
                                post.title,
                                post.published
                        )
                )
                .from(post)
                .where(builder);
    }

    private <T extends PostListItemDto> void applySorting(Pageable pageable, JPAQuery<T> postsQuery) {
        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(post.getType(), post.getMetadata());
            postsQuery.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }
    }

    private JPAQuery<Long> createTotalQuery(BooleanBuilder builder) {
        return jpaQueryFactory
                .select(post.count())
                .from(post)
                .where(builder);
    }
}
