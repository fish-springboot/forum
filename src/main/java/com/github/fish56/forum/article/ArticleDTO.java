package com.github.fish56.forum.article;

import com.github.fish56.forum.validate.ShouldValidate;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
public class ArticleDTO {
    @NotNull(groups = ShouldValidate.OnCreate.class, message = "创建Article的时候必须设置title")
    @ApiModelProperty("文章的标题")
    @Size(min=2, max=20, message = "标题长度应为为2-20")
    private String title;

    @ApiModelProperty("文章的内容")
    private String content;
}
