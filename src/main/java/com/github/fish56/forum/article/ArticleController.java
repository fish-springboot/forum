package com.github.fish56.forum.article;

import com.github.fish56.forum.validate.ShouldValidate;
import com.github.fish56.forum.plate.Plate;
import com.github.fish56.forum.service.ServiceResponse;
import com.github.fish56.forum.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 这里的api也可以设计为
 *   /plates/{plateId}/articles
 */
@Slf4j
@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping
    public Object getArticles(@RequestParam(required = false) Integer plateId,
                              @RequestParam(required = false) Integer authorId,
                              @RequestParam(required = false, defaultValue = "0") Integer page,
                              @RequestParam(required = false, defaultValue = "30") Integer size){
        Article article = new Article();
        if (plateId != null) {
            Plate plate = new Plate().setId(plateId);
            article.setPlate(plate);
        }
        if (authorId != null){
            User user = new User().setId(authorId);
            article.setAuthor(user);
        }
        log.info("用户传递的参数为: " + article.toString());
        Example<Article> articleExample = Example.of(article);

        ServiceResponse serviceResponse =  articleService.findAll(articleExample, PageRequest.of(page, size));
        if (serviceResponse.hasError()){
            return serviceResponse.getErrorResponseEntity();
        } else {
            return serviceResponse.getSuccessResponseEntity();
        }
    }

    @PostMapping
    public ResponseEntity createArticle(@RequestAttribute User user,
                                        @ShouldValidate @RequestBody Article article){
        log.info("用户正在试图发布文章");
        article.setAuthor(user);
        ServiceResponse response = articleService.create(article);
        if (response.hasError()){
            return response.getErrorResponseEntity();
        }
        return response.getSuccessResponseEntity(201);
    }

    @RequestMapping(value = "/{articleId}", method = RequestMethod.PATCH)
    public ResponseEntity updateArticle(@PathVariable Integer articleId,
                                        @ShouldValidate @RequestBody ArticleVo articleVo){
        ServiceResponse<Article> articleResponse = articleService.updateByVo(articleId, articleVo);
        if (articleResponse.hasError()){
            return articleResponse.getErrorResponseEntity();
        }
        return articleResponse.getSuccessResponseEntity(201);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getArticleInfo(@PathVariable Integer id){
        ServiceResponse response = articleService.findById(id);
        if (response.hasError()){
            return response.getErrorResponseEntity();
        }
        return response.getSuccessResponseEntity();
    }
}
