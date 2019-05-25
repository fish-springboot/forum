package com.github.fish56.forum.user;

import com.github.fish56.forum.ForumApplicationTests;
import com.github.fish56.forum.service.ServiceResponse;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class UserServiceImplTest extends ForumApplicationTests {
    @Autowired
    private UserService userService;

    private User user;

    @Before
    public void init(){
        user = new User();
        user.setName("Jack");
        user.setEmail("overwall@gmail.com");
        user.setToken("dsfewwef");
        user.setPassword("12345678910");
    }

    /**
     * 创建一个用户
     */
    @Test
    public void create(){
        ServiceResponse<User> response = userService.create(user);
        System.out.println(response.getData());
        assertTrue(response.getData().getId() == 2);
    }

    /**
     * 修改id为1的用户的信息
     */
    @Test
    public void changeUserInfo1() {
        User user = new User();
        user.setId(1);
        user.setName("Jack");

        ServiceResponse<User> serviceResponse = userService.changeUserInfo(user);

        System.out.println(serviceResponse.getData());
        assertTrue(serviceResponse.getData().getName().equals("Jack"));
    }

    /**
     * 禁止直接修改密码
     */
    @Test
    public void changeUserInfo2(){
        user.setId(1);
        user.setName("Jack");
        user.setPassword("aaaaaaaaaaa");

        ServiceResponse serviceResponse = userService.changeUserInfo(user);

        System.out.println(serviceResponse.getErrorMessage());
        assertTrue(serviceResponse.hasError());
    }

}