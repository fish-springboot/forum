package com.github.fish56.forum.article.comment;

import com.github.fish56.forum.article.Article;
import com.github.fish56.forum.user.User;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.mapping.ToOne;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 评论信息
 */
@Entity
@Data
@Accessors(chain = true)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Article article;

    @OneToOne
    private User author;

    private String content;

    /**
     * 如果这是一个独立的评论，那么这个值应该为null
     * 如果这是回复评论A的评论，那么它的值就是A
     */
    @OneToOne
    private Comment replyTo;

    @CreationTimestamp
    private Timestamp createTime;

    @UpdateTimestamp
    private Timestamp updateTime;
}
