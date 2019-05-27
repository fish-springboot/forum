package com.github.fish56.forum.article;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
public class ArticleVo {
    @Size(min=2, max=20, message = "标题长度应为为2-20")
    private String title;

    private String content;
}
