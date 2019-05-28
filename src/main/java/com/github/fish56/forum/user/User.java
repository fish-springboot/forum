package com.github.fish56.forum.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Entity
@Data
@Accessors(chain = true)
public class User {
    // 但是污染swagger啊，怎么办？
    // @Null(message = "id 应该交给后代的创建")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 这里可以选择name作为唯一键来登录
     */
    @NotNull(message = "姓名不能为空")
    @Size(min=2, max=20)
    @Column(unique = true, nullable = false, length = 20) // 映射为字段，值不能为空
    private String name;

    @NotNull(message = "邮箱不能为空")
    @Size(max=30)
    @Email(message= "邮箱格式不对" )
    @Column(length = 30, unique = true)
    private String email;

    @Column(unique = true)
    private String token;

    /**
     * 如果允许用户通过第三方登录，那么可以允许密码为空
     */
    @NotNull(message = "密码不能为空")
    @Size(min = 10, max = 30, message = "密码长度应该在10-30之间")
    @Column(nullable = false, length = 30)
    private String password;

    @Column(length = 200)
    private String avatar;

    /**
     * 通过UserVo来修改自身的字段
     * @param userDTO
     */
    public void updateByDTO(UserDTO userDTO){
        if (userDTO.getName() != null) {
            name = userDTO.getName();
        }
        if (userDTO.getEmail() != null) {
            email = userDTO.getEmail();
        }
        if (userDTO.getAvatar() != null) {
            avatar = userDTO.getAvatar();
        }
    }

    /**
     * 对于token，password，序列化的时候不要序列化它们
     * 但是解析的时候需要，因为数据是从前端通过字符串传过来的
     * @return
     */
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public User setPassword(String password) {
        this.password = password;
        return this;
    }
    @JsonIgnore
    public String getToken() {
        return token;
    }

    @JsonProperty("token")
    public User setToken(String token) {
        this.token = token;
        return this;
    }
}
