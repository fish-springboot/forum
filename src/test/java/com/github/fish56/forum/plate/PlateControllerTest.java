package com.github.fish56.forum.plate;

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

public class PlateControllerTest extends ForumApplicationTests {

    @Test
    public void getPlateList() throws Exception{
        ResultMatcher isOk = MockMvcResultMatchers.status().is(200);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/plates")
                .header("Authorization", "bearer " + userToken);

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(isOk);
    }

    /**
     * 尝试创建一个版块
     * @throws Exception
     */
    @Test
    public void postPlate() throws Exception{
        ResultMatcher is201 = MockMvcResultMatchers.status().is(201);

        Plate plate = new Plate();
        plate.setTitle("spring");
        plate.setInfo("spring技术交流学习版块");

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/plates")
                .header("Authorization", "bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(plate));

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(is201);
    }

    /**
     * 尝试创建一个版块，但是标题不对，会抛出异常
     * @throws Exception
     */
    @Test
    public void postPlate2() throws Exception{
        ResultMatcher is201 = MockMvcResultMatchers.status().is(201);

        Plate plate = new Plate();
        plate.setTitle("s");
        plate.setInfo("spring技术交流学习版块");

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/plates")
                .header("Authorization", "bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(plate));

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(is201);
    }

    /**
     * 尝试修改id为1的版块的内容
     * @throws Exception
     */
    @Test
    public void editPlates() throws Exception{
        ResultMatcher is200 = MockMvcResultMatchers.status().is(200);
        Plate plate = new Plate();
        plate.setId(1);
        plate.setTitle("spring");
        plate.setInfo("spring吹逼灌水论坛");

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.patch("/plates")
                .header("Authorization", "bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(plate));

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(is200);
    }
}