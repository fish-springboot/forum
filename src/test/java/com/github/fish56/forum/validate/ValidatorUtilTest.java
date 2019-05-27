package com.github.fish56.forum.validate;

import com.github.fish56.forum.user.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValidatorUtilTest {

    @Test
    public void validate() {
        User user = new User();
        String validateMessage = ValidatorUtil.validate(user);
        System.out.println(validateMessage);
    }
}