package com.github.fish56.forum.article.comment;

import com.alibaba.fastjson.JSONObject;
import com.github.fish56.forum.ForumApplicationTests;
import com.github.fish56.forum.article.Article;
import com.github.fish56.forum.user.User;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class CommentControllerTest extends ForumApplicationTests {

    @Test
    public void getCommentList() throws Exception{
        ResultMatcher isOk = MockMvcResultMatchers.status().is(200);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/articles/1/comments");

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(isOk);
    }

    @Test
    public void createComment() throws Exception{
        Comment comment = new Comment();
        User user = new User();
        user.setId(1);
        Article article = new Article();
        article.setId(1);
        comment.setAuthor(user);
        comment.setArticle(article);

        ResultMatcher isOk = MockMvcResultMatchers.status().is(201);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post("/articles/1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(comment));

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(isOk);
    }
}