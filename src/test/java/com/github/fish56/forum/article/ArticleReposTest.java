package com.github.fish56.forum.article;

import com.alibaba.fastjson.JSONObject;
import com.github.fish56.forum.ForumApplicationTests;
import com.github.fish56.forum.user.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ArticleReposTest extends ForumApplicationTests {
    @Autowired
    private ArticleRepos articleRepos;

    /**
     * 简单的分页查询
     *   用时也会查询出article对应的comment
     */
    @Test
    public void find(){
        User user = new User().setId(1);
        Article article = new Article().setAuthor(user);

        Example<Article> articleExample = Example.of(article);

        Iterable<Article> articles = articleRepos.findAll(articleExample, PageRequest.of(1, 10));

        System.out.println(JSONObject.toJSONString(articles));

    }

    @Test
    public void b(){
//        List<Article> articles =
//                articleRepos.findAllBy(new Date(System.currentTimeMillis() - 10000), 2);
//        System.out.println(JSONObject.toJSONString(articles));
    }
}