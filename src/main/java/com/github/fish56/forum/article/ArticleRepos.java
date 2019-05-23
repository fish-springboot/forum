package com.github.fish56.forum.article;

import com.github.fish56.forum.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepos extends JpaRepository<Article, Integer> {
}
