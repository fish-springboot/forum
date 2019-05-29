package com.github.fish56.forum.article;

import com.github.fish56.forum.plate.PlateRepos;
import com.github.fish56.forum.service.ServerResponseMessage;
import com.github.fish56.forum.validate.ShouldValidate;
import com.github.fish56.forum.plate.Plate;
import com.github.fish56.forum.service.ServiceResponse;
import com.github.fish56.forum.user.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Optional;

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

    @ApiOperation("获得所有用户列表")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询文章列表", response = Article.class, responseContainer = "List")
    })
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

    @ApiOperation("发布一个文章")
    @ApiResponses({
            @ApiResponse(code = 201, message = "发布一个文章", response = Article.class)
    })
    @PostMapping
    public ResponseEntity createArticle(@ApiIgnore @RequestAttribute User user,
                                        @RequestParam Integer plateId,
                                        @ShouldValidate(ShouldValidate.OnCreate.class)
                                            @RequestBody ArticleDTO articleDTO){
        // DTO没有对空值进行校验，这里手动的进行校验
        if (articleDTO.getTitle() == null) {
            return ServerResponseMessage.get(400, "title不能为空");

        }
        log.info("用户正在试图发布文章");
        Article article = new Article().setAuthor(user);
        article.updateByDTO(articleDTO);

        ServiceResponse response = articleService.create(plateId, article);
        if (response.hasError()){
            return response.getErrorResponseEntity();
        }
        return response.getSuccessResponseEntity(201);
    }

    @ApiOperation("更新文章的信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "更新一个文章", response = Article.class)
    })
    @RequestMapping(value = "/{articleId}", method = RequestMethod.PATCH)
    public ResponseEntity updateArticle(@PathVariable Integer articleId,
                                        @ShouldValidate @RequestBody ArticleDTO articleDTO){
        ServiceResponse<Article> articleResponse = articleService.updateByVo(articleId, articleDTO);
        if (articleResponse.hasError()){
            return articleResponse.getErrorResponseEntity();
        }
        return articleResponse.getSuccessResponseEntity(200);
    }

    @ApiOperation("获得单个文章的内容")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询到一个文章", response = Article.class)
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getArticleInfo(@PathVariable Integer id){
        ServiceResponse response = articleService.findById(id);
        if (response.hasError()){
            return response.getErrorResponseEntity();
        }
        return response.getSuccessResponseEntity();
    }
}
