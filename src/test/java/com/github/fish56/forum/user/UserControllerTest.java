package com.github.fish56.forum.user;

import com.alibaba.fastjson.JSONObject;
import com.github.fish56.forum.ForumApplicationTests;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.*;

/**
 * 默认数据库中有一个id为1的用户
 *   为了保证测试逻辑的正确性，请勿修改测试数据库的配置
 */
public class UserControllerTest extends ForumApplicationTests {
    /**
     * 查询用户列表
     * @throws Exception
     */
    @Test
    public void getUserList() throws Exception{
        ResultMatcher isOk = MockMvcResultMatchers.status().is(200);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/users");

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(isOk);
    }

    /**
     * 测试是否可以创建一个用户
     * @throws Exception
     */
    @Test
    public void createUser() throws Exception{
        ResultMatcher is201 = MockMvcResultMatchers.status().is(201);
        // 创建后应该返回用户的id
        ResultMatcher hasId = MockMvcResultMatchers
                .jsonPath("$.id").exists();

        User user = new User().setName("Jack")
                .setPassword("12345634534534534")
                .setEmail("sdf@dfsd.com");

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(user));

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(is201)
                .andExpect(hasId);
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
     *   但是因为name字段太短，会被我们的validator拦截，导致返回400状态码
     * @throws Exception
     */
    @Test
    public void changeUserInfo() throws Exception{
        ResultMatcher is400 = MockMvcResultMatchers.status().is(400);

        User user = new User().setId(1)
                .setName("L")
                .setEmail("sdf@dfsd.com");

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.patch("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(user));

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(is400);
    }

    /**
     * 更新id为1的用户的token，
     *   然后需要传递用户的密码，密码是写死在初始化配置的数据库的配置中的
     * @throws Exception
     */
    @Test
    public void getUserToken() throws Exception{
        ResultMatcher is201 = MockMvcResultMatchers.status().is(201);

        String password = "1234567890";
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.patch("/users/1/token")
                .contentType(MediaType.TEXT_PLAIN)
                .content(password);

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(is201);
    }
}