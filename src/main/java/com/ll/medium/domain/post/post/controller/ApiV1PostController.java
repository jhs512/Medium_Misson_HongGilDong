package com.ll.medium.domain.post.post.controller;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.post.post.dto.PostDto;
import com.ll.medium.domain.post.post.dto.PostListItemDto;
import com.ll.medium.domain.post.post.service.PostService;
import com.ll.medium.global.exceptions.GlobalException;
import com.ll.medium.global.rq.Rq.Rq;
import com.ll.medium.global.rsData.RsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/posts", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "ApiV1PostController", description = "글 CRUD 컨트롤러")
@SecurityRequirement(name = "bearerAuth")
public class ApiV1PostController {
    private final Rq rq;
    private final PostService postService;

    public record GetItemsResponseBody(@NonNull List<PostListItemDto> items) {
    }

    @GetMapping(value = "", consumes = ALL_VALUE)
    @Operation(summary = "글 리스트")
    public RsData<GetItemsResponseBody> getItems() {
        Member member = rq.getMember();

        List<PostListItemDto> posts = postService.findByPublished(true, PostListItemDto.class);

        return RsData.of(
                "200",
                "성공",
                new GetItemsResponseBody(posts)
        );
    }

    public record GetMineResponseBody(@NonNull List<PostListItemDto> items) {
    }

    @GetMapping(value = "/mine", consumes = ALL_VALUE)
    @Operation(summary = "내 글 리스트")
    public RsData<GetMineResponseBody> getMine() {
        Member member = rq.getMember();

        List<PostListItemDto> posts = postService.findByAuthor(member, PostListItemDto.class);

        return RsData.of(
                "200",
                "내 글 가져오기 성공",
                new GetMineResponseBody(posts)
        );
    }

    public record GetItemResponseBody(@NonNull PostDto item) {
    }

    @GetMapping(value = "/{id}", consumes = ALL_VALUE)
    @Operation(summary = "글")
    public RsData<GetItemResponseBody> getItem(
            @PathVariable long id
    ) {
        Optional<PostDto> post = postService.findById(id, PostDto.class);

        if (post.isEmpty())
            throw new GlobalException("404-1", "글을 찾을 수 없습니다.");

        return RsData.of(
                "200",
                "성공",
                new GetItemResponseBody(post.get())
        );
    }

    public record MakeTempResponseBody(@NonNull PostDto item) {
    }

    @PostMapping(value = "/temp", consumes = ALL_VALUE)
    @Operation(summary = "임시 글 생성")
    public RsData<MakeTempResponseBody> makeTemp() {
        RsData<PostDto> postRsData = postService.findTempOrMake(rq.getMember(), PostDto.class);

        return postRsData.of(
                new MakeTempResponseBody(postRsData.getData())
        );
    }
}
