package com.github.fish56.forum.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepos extends JpaRepository<User, Integer> {
    /**
     * 通过token来查找某个用户
     *   如果结果是null，就是没有查询到
     * @param token
     * @return
     */
    public User findByToken(String token);

    /**
     * 这里使用Optional来做空值处理
     * @param email
     * @return
     */
    public Optional<User> findByEmail(String email);
}
