package com.github.fish56.forum.article;

import com.alibaba.fastjson.JSONObject;
import com.github.fish56.forum.ForumApplicationTests;
import com.github.fish56.forum.plate.Plate;
import com.github.fish56.forum.service.ServiceResponse;
import com.github.fish56.forum.user.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * 数据库中默认有一个id为 1 的文章
 */
@Slf4j
public class ArticleServiceImplTest extends ForumApplicationTests {
    @Autowired
    private ArticleService articleService;

    /**
     * 用过id来查询一个文章
     */
    @Test
    public void findById(){
        ServiceResponse<Article> response = articleService.findById(1);
        System.out.println(JSONObject.toJSONString(response.getData()));
        assertTrue( response.getData().getId() == 1);
    }

    /**
     * 创建一个文章
     */
    @Test
    public void create() {
        Article article = new Article();
        article.setTitle("好好好");
        article.setContent("sdf");

        User user = new User();
        user.setId(1);
        article.setAuthor(user);

        Plate plate = new Plate();
        plate.setId(1);
        article.setPlate(plate);

        ServiceResponse<Article> response = articleService.create(article);
        log.info(response.getData().toString());
        // 因为数据库目前只有一个记录，所以插入新的数据的id一定是2
        assertTrue(response.getData().getId().equals(2));
    }

    @Test
    public void update(){
        String newTitle = "titlllllle";
        Article article = new Article();
        article.setId(1);
        article.setTitle(newTitle);
        ServiceResponse<Article> serviceResponse = articleService.update(article);

        log.info(serviceResponse.getData().toString());

        // 确保正确的修改了title
        assertEquals(newTitle, serviceResponse.getData().getTitle());

        // 新的article的content的字段为null，所以这个字段应该忽略，而不是将原来的值设置为null
        assertNotNull(serviceResponse.getData().getContent());
    }
}