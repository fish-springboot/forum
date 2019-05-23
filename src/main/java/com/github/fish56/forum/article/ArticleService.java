package com.github.fish56.forum.article;

import com.github.fish56.forum.service.ServiceResponse;
import org.springframework.stereotype.Service;

@Service
public interface ArticleService {
    public ServiceResponse findById(Integer id);
    public ServiceResponse create(Article article);
    public ServiceResponse update(Article article);
}
