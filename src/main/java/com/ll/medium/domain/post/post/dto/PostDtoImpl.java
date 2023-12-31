package com.ll.medium.domain.post.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDtoImpl implements PostDto {
    private long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private long authorId;
    private String authorUsername;
    private String title;
    private String body;
    private boolean published;
}
