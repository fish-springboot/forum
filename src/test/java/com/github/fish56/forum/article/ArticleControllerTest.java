package com.github.fish56.forum.article;

import com.alibaba.fastjson.JSONObject;
import com.github.fish56.forum.ForumApplicationTests;
import com.github.fish56.forum.plate.Plate;
import com.github.fish56.forum.user.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.*;

@Slf4j
public class ArticleControllerTest extends ForumApplicationTests {
    @Test
    public void getArticles() throws Exception{
        ResultMatcher isOk = MockMvcResultMatchers.status().is(200);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/articles");

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(isOk);
    }

    @Test
    public void createArticle() throws Exception {
        Article article = new Article();
        article.setTitle("好好好");
        article.setContent("sdf");
        ResultMatcher isOk = MockMvcResultMatchers.status().is(201);

        // 创建后应该返回id
        ResultMatcher hasId = MockMvcResultMatchers
                .jsonPath("$.id")
                .exists();

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(article.toJSONString());

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(isOk);
    }

    @Test
    public void getArticleInfo() throws Exception{
        ResultMatcher isOk = MockMvcResultMatchers.status().is(200);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/articles/1");

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(isOk);
    }
}