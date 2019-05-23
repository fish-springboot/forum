package com.github.fish56.forum.user;

import com.github.fish56.forum.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepos extends JpaRepository<User, Integer> {
}
