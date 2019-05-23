package com.github.fish56.forum.user;

import com.github.fish56.forum.controller.ServerResponse;
import com.github.fish56.forum.service.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepos userRepos;

    @Autowired
    private UserService userService;

    @GetMapping
    public Iterable<User> getUserList(){
        log.info("正在查询用户列表");
        return userRepos.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        log.info("正在创建用户");
        log.info(user.toString());
        return userRepos.save(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object getUserInfo(@PathVariable Integer id){
        log.info("获取用户信息详情");
        Optional<User> userOptional =  userRepos.findById(id);
        if (userOptional.isPresent()){
            return userOptional.get();
        } else {
            return ServerResponse.createErrorMessage(404, "用户不存在");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public Object changeUserInfo(@PathVariable Integer id, @RequestBody User user){
        log.info("修改用户信息");
        ServiceResponse serviceResponse = userService.changeUserInfo(user);
        if (serviceResponse.hasError()){
            return serviceResponse.getErrorResponseEntity();
        }else {
            return serviceResponse.getSuccessResponseEntity(201);
        }
    }
}
