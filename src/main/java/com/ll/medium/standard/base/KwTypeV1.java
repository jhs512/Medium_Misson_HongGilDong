package com.ll.medium.standard.base;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum KwTypeV1 {
    TITLE("title"),
    TITLE_OR_BODY("title,body"),
    NAME("name"),
    ALL("all");

    private final String value;
}
