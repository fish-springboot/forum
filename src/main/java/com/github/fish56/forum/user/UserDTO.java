package com.github.fish56.forum.user;

import com.github.fish56.forum.validate.ShouldValidate;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

/**
 * 用户修改自己的信息的时候，用这个对象来接受用户传递的数据
 *
 * token、password 不可以直接修改，所以没有出现在这里
 *
 * 然后都没有用@NotNull来标注，因为我们认为用户可以忽略某些字段
 */
@Data
@Accessors(chain = true)
public class UserDTO {

    @NotNull(groups = ShouldValidate.OnCreate.class, message = "创建User的时候必须设置name")
    @Size(min=2, max=20)
    private String name;

    @NotNull(groups = ShouldValidate.OnCreate.class, message = "创建User的时候必须设置email")
    @Size(max=30)
    @Email(message= "邮箱格式不对" )
    private String email;

    private String avatar;

    /**
     * 如果允许用户通过第三方登录，那么可以允许密码为空
     */
    @NotNull(groups = ShouldValidate.OnCreate.class, message = "创建用户时，密码不能为空")
    @Null(groups = ShouldValidate.OnUpdate.class, message = "修改用户信息的时候不能直接修改密码")
    @Size(min = 10, max = 30, message = "密码长度应该在10-30之间")
    @Column(nullable = false, length = 30)
    private String password;
}
