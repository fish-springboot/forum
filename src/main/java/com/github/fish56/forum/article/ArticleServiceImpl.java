package com.github.fish56.forum.article;

import com.github.fish56.forum.service.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepos articleRepos;

    @Override
    public ServiceResponse findById(Integer id) {
        log.info("正在查询文章" + id);
        Optional<Article> optionalArticle = articleRepos.findById(id);
        if (!optionalArticle.isPresent()) {
            log.info("查询的文章不存在");
            return ServiceResponse.getInstance(404, "当前文章不存在");
        }
        log.info("查询到文章: " + optionalArticle.get().toString());
        return ServiceResponse.getInstance(optionalArticle.get());
    }

    /**
     * 假如创建文章时，传入的article中存在id字段，该如何处理？
     *   - 报错：提醒前端不应该传入id
     *   - 忽略
     *   - 按照传入的id来创建（这就给前端比较大的权限了）
     *
     *   没有固定的处理方式，这里我选择忽略这个字段
     * @param article
     * @return
     */
    @Override
    public ServiceResponse create(Article article) {
        log.info("正在将文章信息插入数据库: " + article.toString());
        article.setId(null);
        articleRepos.save(article);
        return ServiceResponse.getInstance(article);
    }

    @Override
    public ServiceResponse update(Article article) {
        log.info("正在更新文章信息");
        Optional<Article> optionalArticle = articleRepos.findById(article.getId());

        if (!optionalArticle.isPresent()) {
            log.info("试图更新的文章不存在");
            return ServiceResponse.getInstance(404, "试图更新的文章不存在");
        }

        Article articleInDB = optionalArticle.get();
        articleInDB.updateByArticle(article);
        articleRepos.save(articleInDB);
        return ServiceResponse.getInstance(articleInDB);
    }
}
