package com.github.fish56.forum.user;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserReposTest extends UserBaseTest {
    @Test
    public void save(){
       userRepos.save(user);
       System.out.println(user);
       // User(id=1, name=Jon, email=overwall@gmail.com, to
    }
}