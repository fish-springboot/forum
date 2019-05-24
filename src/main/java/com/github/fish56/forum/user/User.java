package com.github.fish56.forum.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "姓名不能为空")
    @Size(min=2, max=20)
    @Column(nullable = false, length = 20) // 映射为字段，值不能为空
    private String name;

    @NotNull(message = "邮箱不能为空")
    @Size(max=50)
    @Email(message= "邮箱格式不对" )
    @Column(nullable = false, length = 50, unique = true)
    private String email;

    private String token;

    @NotNull(message = "密码不能为空")
    @Size(min = 10, max = 30, message = "密码长度应该在10-30之间")
    @Column(length = 100)
    private String password;

    @Column(length = 200)
    private String avatar;

    /**
     * 确保在更新时只更新部分字段
     * @param user
     */
    public void updateWithNewValue(User user){
        if (user.getName() != null) {
            name = user.getName();
        }
        if (user.getEmail() != null) {
            email = user.getEmail();
        }
        if (user.getAvatar() != null) {
            avatar = user.getAvatar();
        }
        if (user.getToken() != token){
            token = user.getToken();
        }
    }

    /**
     * 对于token，password，序列化的时候不要序列化它们
     * 但是解析的时候需要，因为数据是从前端通过字符串传过来的
     * @return
     */
    //@JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    //@JsonIgnore
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
