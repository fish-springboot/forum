package com.github.fish56.forum.user;

import com.github.fish56.forum.service.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepos userRepos;

    @Override
    public ServiceResponse create(User user) {
        log.info("正在创建用户" + user.getName());
        user.setId(null);
        User newUser = userRepos.save(user);
        if (newUser == null){
            return ServiceResponse.getInstance(400, "无法将用户插入数据库");
        } else {
            return ServiceResponse.getInstance(newUser);
        }
    }

    @Override
    public ServiceResponse changeUserInfo(User userNewState) {
        Optional<User> userOptional = userRepos.findById(userNewState.getId());
        if (!userOptional.isPresent()){
            return ServiceResponse.getInstance(404, "用户不存在");
        }
        if (userNewState.getPassword() != null) {
            return ServiceResponse.getInstance(401, "禁止直接修改密码");
        }
        if (userNewState.getToken() != null) {
            return ServiceResponse.getInstance(401, "禁止直接修改token");
        }

        User user = userOptional.get();
        user.updateWithNewValue(userNewState);
        userRepos.save(user);

        return ServiceResponse.getInstance(user);
    }
}
