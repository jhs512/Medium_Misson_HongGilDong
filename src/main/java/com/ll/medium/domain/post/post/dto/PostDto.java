package com.ll.medium.domain.post.post.dto;

import com.ll.medium.standard.base.HasConcreteDto;
import org.springframework.lang.NonNull;

public interface PostDto extends AbsPostDto, HasConcreteDto {
    @NonNull
    String getBody();
}

