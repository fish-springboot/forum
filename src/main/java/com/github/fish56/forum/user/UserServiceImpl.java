package com.github.fish56.forum.user;

import com.github.fish56.forum.service.ServiceResponse;
import com.github.fish56.forum.validate.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
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
    public ServiceResponse update(Integer userId, UserVo userVo) {
        Optional<User> userOptional = userRepos.findById(userId);
        if (!userOptional.isPresent()){
            return ServiceResponse.getInstance(404, "试图修改的用户不存在");
        }

        User user = userOptional.get();
        user.updateByVo(userVo);

        // 校验更新后的用户是否符合规则
        String errorMessage = ValidatorUtil.validate(user);
        if (errorMessage != null) {
            return ServiceResponse.getInstance(400, errorMessage);
        }

        userRepos.save(user);

        return ServiceResponse.getInstance(user);
    }

    @Override
    public User updateToken(User user) {
        String token = RandomStringUtils.randomAlphanumeric(30);
        user.setToken(token);
        userRepos.save(user);
        return user;
    }

    @Override
    public Boolean checkByEmail(String email, String password) {
        Optional<User> userOptional = userRepos.findByEmail(email);
        return userOptional.isPresent() && userOptional.get().getPassword().equals(password);
    }
    @Override
    public Boolean checkById(Integer id, String password) {
        Optional<User> userOptional = userRepos.findById(id);
        return userOptional.isPresent() && userOptional.get().getPassword().equals(password);
    }
}
