package com.github.fish56.forum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ForumApplicationTests {
    @Autowired
    protected MockMvc mockMvc;

    /**
     * 测试数据库id为1的用户的token
     */
    protected String userToken = "hello_token";

    @Test
    public void contextLoads() {
    }
}
