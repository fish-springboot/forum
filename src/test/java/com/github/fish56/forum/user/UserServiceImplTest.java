package com.github.fish56.forum.user;

import com.alibaba.fastjson.JSONObject;
import com.github.fish56.forum.ForumApplicationTests;
import com.github.fish56.forum.service.ServiceResponse;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.*;

public class UserServiceImplTest extends UserBaseTest {
    @Autowired
    private UserService userService;

    /**
     * 修改用户信息
     * @throws Exception
     */
    @Test
    public void changeUserInfo1() throws Exception{
        userRepos.save(user);

        user.setName("Jack");

        ServiceResponse serviceResponse = userService.changeUserInfo(user);

        if (serviceResponse.hasError()){
            System.out.println(JSONObject.toJSONString(
                    serviceResponse.getErrorMessage()));
            // "禁止直接修改密码"
        }else {
            System.out.println(serviceResponse.getData());
        }
    }
    @Test
    public void changeUserInfo2() throws Exception{
        userRepos.save(user);

        user.setName("Jack");
        user.setPassword(null);
        user.setToken(null);

        ServiceResponse serviceResponse = userService.changeUserInfo(user);

        if (serviceResponse.hasError()){
            System.out.println(JSONObject.toJSONString(
                    serviceResponse.getErrorMessage()));
            // "禁止直接修改密码"
        }else {
            System.out.println(serviceResponse.getData());
        }
    }

}