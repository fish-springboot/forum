package com.github.fish56.forum.article.comment;

import com.github.fish56.forum.article.Article;
import com.github.fish56.forum.user.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/articles/{articleId}/comments")
public class CommentController {
    @Autowired
    private CommentRepos commentRepos;

    @ApiOperation("获得某个文章的内容")
    @GetMapping
    public List<Comment> getCommentList(@PathVariable Integer articleId){
        Article article = new Article();
        article.setId(articleId);
        return commentRepos.findByArticle(article);
    }

    @ApiOperation("向某个文章提交评论")
    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CommentDTO commentDTO,
                                                 @ApiIgnore @RequestAttribute User user,
                                                 @PathVariable Integer articleId){
        Comment comment = new Comment()
                .setAuthor(user)
                .setArticle(new Article().setId(articleId));
       comment.updateByDTO(commentDTO);

        return ResponseEntity.status(201).body(commentRepos.save(comment));
    }
}
