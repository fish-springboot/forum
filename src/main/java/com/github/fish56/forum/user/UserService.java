package com.github.fish56.forum.user;

import com.github.fish56.forum.service.ServiceResponse;

public interface UserService {
    public ServiceResponse create(User user);
    public ServiceResponse changeUserInfo(User userNewState);

}
