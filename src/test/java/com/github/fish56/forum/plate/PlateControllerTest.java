package com.github.fish56.forum.plate;

import com.github.fish56.forum.ForumApplicationTests;
import com.github.fish56.forum.user.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.*;

public class PlateControllerTest extends ForumApplicationTests {
    @Test
    public void plates() throws Exception{
        ResultMatcher isOk = MockMvcResultMatchers.status().is(200);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/plates");

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(isOk);
    }
}