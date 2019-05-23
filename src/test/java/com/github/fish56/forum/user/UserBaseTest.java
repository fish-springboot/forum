package com.github.fish56.forum.user;

import com.github.fish56.forum.ForumApplicationTests;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

public class UserBaseTest extends ForumApplicationTests {
    @Autowired
    protected UserRepos userRepos;

    protected User user;

    @Before
    public void init(){
        user = new User();
        user.setName("Jon");
        user.setEmail("overwall@gmail.com");
        user.setToken("dsfewwef");
        user.setPassword("12345678910");
    }
}