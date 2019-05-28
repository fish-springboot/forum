package com.github.fish56.forum.user;

import com.github.fish56.forum.validate.ShouldValidate;
import com.github.fish56.forum.service.ServerResponseMessage;
import com.github.fish56.forum.service.ServiceResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepos userRepos;

    @Autowired
    private UserService userService;

    @ApiOperation("查询所有用户的信息")
    @GetMapping
    public Iterable<User> getUserList(){
        log.info("正在查询用户列表");
        return userRepos.findAll();
    }

    /**
     * 这里使用了一般的方法作为参数校验，可以看到：
     *   - 侵入性很强
     *   - 模板代码冗长
     * @param userDTO
     * @param bindingResult
     * @return
     */
    @ApiOperation("创建一个用户")
    @ApiResponse(code = 201, message = "成功创建用户", response = User.class)
    @PostMapping
    public ResponseEntity createUser(@Validated(ShouldValidate.OnCreate.class) @RequestBody UserDTO userDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            StringBuilder errorMessage = new StringBuilder();

            bindingResult.getAllErrors().forEach((objectError) -> {
                // 将错误信息输出到errorMessage中
                errorMessage.append(objectError.getDefaultMessage() + ", ");

            });
            return ServerResponseMessage.get(400, errorMessage.toString());
        }
        if (userDTO.getEmail() == null) {

        }
        User user = new User();
        //user.updateByDTO();

        log.info("正在创建用户");
        log.info(user.toString());
        // 这里我们认为id应给交给数据库来生成，所以我们手动清除用户可能传递来的id
        user.setId(null);
        ServiceResponse serviceResponse = userService.create(user);
        if (serviceResponse.hasError()) {
            return serviceResponse.getErrorResponseEntity();
        } else {
            return serviceResponse.getSuccessResponseEntity(201);
        }
    }

    @ApiOperation("查询一个用户的信息")
    @ApiResponse(code = 200, message = "查询到用户信息", response = User.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object getUserInfo(@PathVariable Integer id){
        log.info("获取用户信息详情");
        Optional<User> userOptional =  userRepos.findById(id);
        if (userOptional.isPresent()){
            return userOptional.get();
        } else {
            return ServerResponseMessage.get(404, "用户不存在");
        }
    }

    @ApiOperation("修改一个用户的信息")
    @ApiResponse(code = 200, message = "修改了用户信息", response = User.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public Object changeUserInfo(@PathVariable Integer id,
                                 @Validated @RequestBody UserDTO userDTO){
        log.info("修改用户信息");
        ServiceResponse serviceResponse = userService.update(id, userDTO);
        if (serviceResponse.hasError()){
            return serviceResponse.getErrorResponseEntity();
        }else {
            return serviceResponse.getSuccessResponseEntity(200);
        }
    }

    @ApiOperation("更新一个用户的token")
    @RequestMapping(value = "/{id}/token", method = RequestMethod.PATCH)
    public Object getUserToken(@PathVariable Integer id,
                               @ApiParam(value = "修改token时是需要传入用户的密码")
                               @RequestBody String password){
        Optional<User> userOptional = userRepos.findById(id);
        if (!userOptional.isPresent()) {
            return ServerResponseMessage.get(404, "用户不存在");
        }

        User user = userOptional.get();

        if (userService.checkById(id, password)){
            log.info("正在更新用户的token");

            userService.updateToken(user);

            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("token", user.getToken());
            return ResponseEntity.status(201).body(tokenMap);

        } else {
            log.info("密码和id不匹配");
            return ServerResponseMessage.get(401, "密码和id不匹配");
        }
    }
}
