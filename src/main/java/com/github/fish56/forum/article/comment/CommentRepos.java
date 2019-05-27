package com.github.fish56.forum.article.comment;

import com.github.fish56.forum.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepos extends JpaRepository<Comment, Integer> {
    public List<Comment> findByArticle(Article article);
}
