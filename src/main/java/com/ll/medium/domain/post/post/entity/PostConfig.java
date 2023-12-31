package com.ll.medium.domain.post.post.entity;

import lombok.Getter;

@Getter
public class PostConfig {
    private String title;
    private boolean open;
    private String body;
    private String pureBody;

    public PostConfig(String body) {
        this.body = body;
        title = "파싱에러";
        open = false;

        String[] bodyBits = body.trim().split("\\$\\$", 3);

        if (bodyBits.length != 3) {
            return;
        }

        this.pureBody = bodyBits[2].trim();

        String configStr = bodyBits[1];

        String[] lines = configStr.split("\n");
        for (String line : lines) {
            if (line.startsWith("title: ")) {
                this.title = line.substring("title: ".length());
            } else if (line.startsWith("open: true")) {
                this.open = Boolean.parseBoolean(line.substring("open: ".length()));
            } else if (line.startsWith("open: false")) {
                this.open = Boolean.parseBoolean(line.substring("open: ".length()));
            }
        }
    }

    public PostConfig(String title, boolean open, String pureBody) {
        this.title = title;
        this.open = open;
        this.pureBody = pureBody;
        this.body = """
                $$config
                title: %s
                open: %s
                $$
                %s
                """
                .formatted(title, open, pureBody)
                .stripIndent()
                .trim();
    }
}
