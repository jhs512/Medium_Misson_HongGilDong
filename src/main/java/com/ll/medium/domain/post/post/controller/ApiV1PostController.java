package com.ll.medium.domain.post.post.controller;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.post.post.dto.PostDto;
import com.ll.medium.domain.post.post.dto.PostListItemDto;
import com.ll.medium.domain.post.post.entity.Post;
import com.ll.medium.domain.post.post.service.PostService;
import com.ll.medium.global.app.AppConfig;
import com.ll.medium.global.exceptions.GlobalException;
import com.ll.medium.global.globalMapper.GlobalMapper;
import com.ll.medium.global.rq.Rq.Rq;
import com.ll.medium.global.rsData.RsData.RsData;
import com.ll.medium.standard.base.KwTypeV1;
import com.ll.medium.standard.base.PageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    private final GlobalMapper globalMapper;

    public record GetItemsResponseBody(@NonNull PageDto<PostListItemDto> itemPage) {
        public GetItemsResponseBody(Page<PostListItemDto> page) {
            this(new PageDto<>(page));
        }
    }

    @GetMapping(value = "", consumes = ALL_VALUE)
    @Operation(summary = "글 리스트")
    public RsData<GetItemsResponseBody> getItems(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String kw,
            @RequestParam(defaultValue = "all") KwTypeV1 kwType
    ) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page - 1, AppConfig.getBasePageSize(), Sort.by(sorts));
        Page<PostListItemDto> itemPage = postService.findByKw(kwType, kw, null, true, pageable, PostListItemDto.class);

        return RsData.of(
                "200",
                "성공",
                new GetItemsResponseBody(itemPage)
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

    public record EditRequestBody(@NonNull String body) {
    }

    public record EditResponseBody(@NonNull PostDto item) {
    }

    @PutMapping(value = "/{id}", consumes = ALL_VALUE)
    @Operation(summary = "글 수정")
    public RsData<EditResponseBody> edit(
            @PathVariable long id,
            @RequestBody EditRequestBody requestBody
    ) {
        Optional<Post> opPost = postService.findById(id, Post.class);

        if (opPost.isEmpty())
            throw new GlobalException("404-1", "글을 찾을 수 없습니다.");

        if (!postService.canEdit(rq.getMember(), opPost.get())) {
            throw new GlobalException("403-1", "권한이 없습니다.");
        }

        postService.edit(opPost.get(), requestBody.body);

        return RsData.of(
                "200",
                "성공",
                new EditResponseBody(globalMapper.map(opPost.get(), PostDto.class))
        );
    }
}
