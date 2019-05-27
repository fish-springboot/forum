package com.github.fish56.forum.article;

import com.github.fish56.forum.service.ServiceResponse;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ArticleService {
    public ServiceResponse findById(Integer id);

    /**
     * 条件查询文章列表，并做分页处理
     * @param articleExample
     * @param page
     * @return
     */
    public ServiceResponse<Article> findAll(Example<Article> articleExample, Pageable page);
    public ServiceResponse<Article> create(Article article);
    public ServiceResponse<Article> updateByVo(Integer articleId, ArticleVo articleVo);
}
