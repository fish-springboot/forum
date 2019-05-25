package com.github.fish56.forum.user;

import com.alibaba.fastjson.JSONObject;
import com.github.fish56.forum.ForumApplicationTests;
import org.junit.Test;
import org.junit.experimental.results.ResultMatchers;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.*;

/**
 * 默认配置数据有一个id为1的用户
 */
public class UserControllerTest extends ForumApplicationTests {
    @Test
    public void getUserList() throws Exception{
        ResultMatcher isOk = MockMvcResultMatchers.status().is(200);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/users");

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(isOk);
    }

    /**
     * 是否可以创建一个用户
     * @throws Exception
     */
    @Test
    public void createUser() throws Exception{
        ResultMatcher is201 = MockMvcResultMatchers.status().is(201);

        User user = new User();
        user.setName("Jack");
        user.setPassword("12345634534534534");
        user.setEmail("sdf@dfsd.com");
        System.out.println(JSONObject.toJSONString(user));
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(user));


        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(is201);
    }

    /**
     * 查询一个id为1的用户的信息
     * @throws Exception
     */
    @Test
    public void getUserInfo() throws Exception{
        ResultMatcher isOk = MockMvcResultMatchers.status().is(200);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/users/1");

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(isOk);
    }

    /**
     * 修改id为1的用户的信息
     * @throws Exception
     */
    @Test
    public void changeUserInfo() throws Exception{
        ResultMatcher isOk = MockMvcResultMatchers.status().is(200);

        User user = new User();
        user.setId(1);
        user.setName("Jon");
        user.setEmail("sdf@dfsd.com");

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.patch("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(user));

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(isOk);
    }
}