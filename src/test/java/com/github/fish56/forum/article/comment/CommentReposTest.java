package com.github.fish56.forum.article.comment;

import com.alibaba.fastjson.JSONObject;
import com.github.fish56.forum.ForumApplicationTests;
import com.github.fish56.forum.article.Article;
import com.github.fish56.forum.article.ArticleRepos;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class CommentReposTest extends ForumApplicationTests {
    @Autowired
    private CommentRepos commentRepos;
    @Autowired
    private ArticleRepos articleRepos;

    /**
     * 查询某个文章对应的评论
     */
    @Test
    public void getComments(){
        Article article = new Article().setId(1);
        List<Comment> comments = commentRepos.findByArticle(article);
        System.out.println(JSONObject.toJSONString(comments));
    }

    /**
     * 查询某个文章
     *   该文章对应的评论也会被查询出来
     *   直接插入数据库的查不出来的。。。服了
     */
    @Test
    public void getComments2(){
        Comment comment = new Comment().setContent("一个评论");
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        Article article = new Article()
                .setId(1).setTitle("评论")
                .setCreateTime(new Date())
                .setComments(comments);
        articleRepos.save(article);


        Optional<Article> articleOptional = articleRepos.findById(1);
        System.out.println(JSONObject.toJSONString(
                articleOptional.get()));
    }
}