package com.github.fish56.forum.comment;

import com.github.fish56.forum.article.Article;
import com.github.fish56.forum.user.User;
import lombok.Data;

import javax.persistence.*;

/**
 * 评论信息
 */
@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne
    private Article article;

    @OneToOne
    private User author;

    private String content;

    /**
     * 如果这是一个独立的评论，那么这个值应该为null
     * 如果这是回复评论A的评论，那么它的值就是A
     */
    @OneToOne
    private Comment replayTo;

    //    @Column(nullable = false) // 映射为字段，值不能为空
    //    @CreationTimestamp  // 由数据库自动创建时间
    //    private Timestamp createTime;
    //
    //    @Column(nullable = false) // 映射为字段，值不能为空
    //    @UpdateTimestamp  // 由数据库更新
    //    private Timestamp updateTime;
}
