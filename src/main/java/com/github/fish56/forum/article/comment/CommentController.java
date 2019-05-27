package com.github.fish56.forum.article.comment;

import com.github.fish56.forum.article.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles/{articleId}/comments")
public class CommentController {
    @Autowired
    private CommentRepos commentRepos;

    @GetMapping
    public List<Comment> getCommentList(@PathVariable Integer articleId){
        Article article = new Article();
        article.setId(articleId);
        return commentRepos.findByArticle(article);
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment){
        return ResponseEntity.status(201).body(commentRepos.save(comment));
    }
}
