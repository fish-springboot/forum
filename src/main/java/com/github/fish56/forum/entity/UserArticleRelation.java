package com.github.fish56.forum.entity;

import com.github.fish56.forum.article.Article;
import com.github.fish56.forum.user.User;
import lombok.Data;

import javax.persistence.*;

/**
 * 记录用户和文章之间的关系
 *   - 用户是否给某个文章点赞了
 *   - 用户是否收藏了某篇文章
 *
 * 如果用户没有执行上述操作，数据库中应该没有记录
 * 也就是说，插入记录时up 和 collect中至少有一个为false
 * 当然，用户后来可以取消操作，至于取消操作是是否需要删除记录，由程序决定
 */
@Entity
@Data
public class UserArticleRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @OneToOne
    private User user;

    @OneToOne
    private Article article;

    /**
     * 用户是否喜欢这篇文章
     */
    private Boolean up;

    /**
     * 用户是否收藏了这篇文章
     */
    private Boolean collect;

}
