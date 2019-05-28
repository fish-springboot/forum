package com.github.fish56.forum.article;

import com.alibaba.fastjson.JSONObject;
import com.github.fish56.forum.ForumApplicationTests;
import com.github.fish56.forum.plate.Plate;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Slf4j
public class ArticleControllerTest extends ForumApplicationTests {
    /**
     * 查询id为1的用户发表的文章
     * @throws Exception
     */
    @Test
    public void getArticles() throws Exception{
        ResultMatcher isOk = MockMvcResultMatchers.status().is(200);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/articles")
                .param("authorId", "1");

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(isOk);
    }

    @Test
    public void createArticle() throws Exception {
        Plate plate = new Plate().setId(1);
        Article article = new Article()
                .setTitle("好好好").setContent("这是一个文章正文内容")
                .setPlate(plate);

        ResultMatcher isOk = MockMvcResultMatchers.status().is(201);
        // 创建后应该返回id
        ResultMatcher hasId = MockMvcResultMatchers
                .jsonPath("$.id")
                .exists();

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/articles")
                .header("Authorization", "bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(article.toJSONString());

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(isOk)
                .andExpect(hasId);
    }

    /**
     * 标题长度不够，所以会被Validator给拦截
     * @throws Exception
     */
    @Test
    public void createArticle2() throws Exception {
        ArticleDTO articleDTO = new ArticleDTO()
                .setTitle("t");

        ResultMatcher isOk = MockMvcResultMatchers.status().is(400);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/articles")
                .header("Authorization", "bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(articleDTO));

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