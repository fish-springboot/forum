package com.github.fish56.forum.article;

import com.github.fish56.forum.service.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleRepos articleRepos;

    @Autowired
    private ArticleService articleService;

    @GetMapping
    public List<Article> getArticles(){
        return articleRepos.findAll();
    }

    @PostMapping
    public ResponseEntity createArticle(@RequestBody Article article){
        ServiceResponse<Article> response = articleService.create(article);
        if (response.hasError()){
            return response.getErrorResponseEntity();
        }
        return response.getSuccessResponseEntity(201);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getArticleInfo(@PathVariable Integer id){
        ServiceResponse<Article> response = articleService.findById(id);
        if (response.hasError()){
            return response.getErrorResponseEntity();
        }
        return response.getSuccessResponseEntity();
    }
}
