package com.recipe.recipearticle;

import com.recipe.recipearticle.notice.entity.Article;
import com.recipe.recipearticle.notice.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RecipeArticleApplicationTests {
    @Autowired
    ArticleRepository articleRepository;

    @Test
    void contextLoads() {
        Article article = articleRepository.findById(1L).get();
        System.out.println(article);
    }

}
