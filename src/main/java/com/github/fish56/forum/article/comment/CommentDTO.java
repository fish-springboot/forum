package com.github.fish56.forum.article.comment;

import com.github.fish56.forum.article.Article;
import com.github.fish56.forum.user.User;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 评论信息
 */
@Data
@Accessors(chain = true)
public class CommentDTO {

    private String content;

    /**
     * 向第i个评论回复
     */
    private Integer replyToId;

}
