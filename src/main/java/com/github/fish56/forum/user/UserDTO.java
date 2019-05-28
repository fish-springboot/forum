package com.github.fish56.forum.user;

import com.github.fish56.forum.validate.ShouldValidate;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
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
}
