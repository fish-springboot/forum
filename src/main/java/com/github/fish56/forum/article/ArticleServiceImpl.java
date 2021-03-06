package com.github.fish56.forum.article;

import com.github.fish56.forum.plate.Plate;
import com.github.fish56.forum.plate.PlateRepos;
import com.github.fish56.forum.service.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private PlateRepos plateRepos;
    @Autowired
    private ArticleRepos articleRepos;

    @Override
    public ServiceResponse findAll(Example<Article> articleExample, Pageable page) {
        Page<Article> articlePage = articleRepos.findAll(articleExample, page);
        return ServiceResponse.getInstance(articlePage.iterator());
    }

    @Override
    public ServiceResponse findById(Integer id) {
        log.info("正在查询文章" + id);
        Optional<Article> optionalArticle = articleRepos.findById(id);
        if (!optionalArticle.isPresent()) {
            log.info("查询的文章不存在");
            return ServiceResponse.getInstance(404, "当前文章不存在");
        } else {
            log.info("查询到文章: " + optionalArticle.get().toString());
            return ServiceResponse.getInstance(optionalArticle.get());
        }
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
    public ServiceResponse create(Integer plateId, Article article) {
        Optional<Plate> plateOptional = plateRepos.findById(plateId);
        if (!plateOptional.isPresent()){
            return ServiceResponse.getInstance(404, "版块" + plateId + "不存在");
        }
        article.setPlate(plateOptional.get());
        article.setId(null);

        log.info("正在将文章信息插入数据库: " + article.toString());
        articleRepos.save(article);
        return ServiceResponse.getInstance(article);
    }

    @Override
    public ServiceResponse updateByVo(Integer articleId, ArticleDTO articleDTO) {
        log.info("正在更新文章信息");
        Optional<Article> optionalArticle = articleRepos.findById(articleId);

        if (!optionalArticle.isPresent()) {
            log.info("试图更新的文章不存在");
            return ServiceResponse.getInstance(404, "试图更新的文章不存在");
        }

        Article article = optionalArticle.get();
        article.updateByDTO(articleDTO);
        articleRepos.save(article);
        return ServiceResponse.getInstance(article);
    }
}
