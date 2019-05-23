package com.github.fish56.forum.article;

import com.alibaba.fastjson.JSONObject;
import com.github.fish56.forum.plate.Plate;
import com.github.fish56.forum.user.User;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 文章实体
 */
@Entity
@Data
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min=2, max=20, message = "标题长度应为为2-20")
    @Column(nullable = false) // 映射为字段，值不能为空
    private String title;

    @Lob
    @Basic(fetch=FetchType.LAZY)
    private String content;

    @OneToOne
    private User author;

    @OneToOne
    private Plate plate;

    private State state;

    public void updateByArticle(Article article){
        if (article.title != null){
            this.title = article.title;
        }
        if (article.content != null){
            this.content = article.content;
        }
        if (article.author != null) {
            this.author = article.author;
        }
        if (article.plate != null) {
            this.plate = article.plate;
        }
        if (article.state != null) {
            this.state = article.state;
        }
    }

    public String toJSONString(){
        return JSONObject.toJSONString(this);
    }

//    @Column(nullable = false) // 映射为字段，值不能为空
//    @CreationTimestamp  // 由数据库自动创建时间
//    private Timestamp createTime;
//
//    @Column(nullable = false) // 映射为字段，值不能为空
//    @UpdateTimestamp  // 由数据库更新
//    private Timestamp updateTime;

    /**
     * Normal: 普通文章
     * Essence: 精华文章
     * Noticed: 管理发出的通知型文章（一般会被前端设置为红色的那种）
     * Locked: 被锁定的文章
     * Deleted: 被删除的文章（不存在于列表，但是存在于数据库中的）
     *
     * - 是否置顶
     * - 是否是热门
     *   这种信息不应该存在于这里，这里只记录状态，不记录行为
     */
    public static enum State{
        Normal, Essence,
        Noticed, Locked, Deleted
    }
}
