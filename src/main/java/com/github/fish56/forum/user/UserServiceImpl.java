package com.github.fish56.forum.user;

import com.github.fish56.forum.service.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepos userRepos;

    @Override
    public ServiceResponse changeUserInfo(User userNewState) {
        Optional<User> userOptional = userRepos.findById(userNewState.getId());
        if (!userOptional.isPresent()){
            return ServiceResponse.getInstance(404, "用户不存在");
        }
//        if (userNewState.getPassword() != null) {
//            return ServiceResponse.getInstance(401, "禁止直接修改密码");
//        }
//        if (userNewState.getToken() != null) {
//            return ServiceResponse.getInstance(401, "禁止直接修改密码");
//        }

        User user = userOptional.get();
        user.updateWithNewValue(userNewState);
        userRepos.save(user);

        if (user == null) {
            return ServiceResponse.getInstance(500, "无法将记录插入数据");
        }
        return ServiceResponse.getInstance(user);
    }
}
