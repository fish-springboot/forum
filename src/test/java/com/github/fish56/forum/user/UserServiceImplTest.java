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
        user = new User().setName("Jack")
                .setEmail("overwall@gmail.com")
                .setToken("dsfewwef")
                .setPassword("12345678910");
    }

    /**
     * 创建一个用户
     *   应为默认数据中已经有一个id为1的用户了，
     *   所以保存用户后，它的id应该是为2的
     */
    @Test
    public void create(){
        ServiceResponse<User> response = userService.create(user);
        System.out.println(response.getData());
        assertTrue(response.getData().getId().equals(2));
    }

    /**
     * 修改id为1的用户的姓名
     */
    @Test
    public void update() {
        String newName = "Jack";
        UserVo userVo = new UserVo().setName(newName);

        ServiceResponse<User> serviceResponse = userService.update(1, userVo);

        System.out.println(serviceResponse.getData());
        assertEquals(newName, serviceResponse.getData().getName());
    }
}