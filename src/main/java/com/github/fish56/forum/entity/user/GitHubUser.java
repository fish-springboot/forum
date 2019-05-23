package com.github.fish56.forum.entity.user;

import com.github.fish56.forum.user.User;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * 关联到GitHub账号
 * 如果用户是直接使用GitHub账号登录的
 *   那么同时创建User，并设置基本信息
 */
@Entity
@Data
public class GitHubUser {
    // GitHub 用户唯一标识符
    @Id
    private String login;

    @OneToOne
    private User user;

    // 访问GitHub使用的token
    private String accessToken;
}
