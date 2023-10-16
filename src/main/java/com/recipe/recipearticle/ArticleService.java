package com.recipe.recipearticle;

import com.recipe.recipearticle.Dto.ArticleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {
  private final ArticleRepository articleRepository;

  public void create(ArticleDto articleDto, long memberId) {
    Article article = Article.builder()
        .subject(articleDto.getSubject())
        .content(articleDto.getContent())
        .memberId(memberId)
        .build();
    articleRepository.save(article);
  }

  public Article modify(ArticleDto articleDto, long member_id) {
    Article article = articleRepository.findById(member_id).get();
    article.setSubject(articleDto.getSubject());
    article.setContent(articleDto.getContent());
    articleRepository.save(article);

    return article;
  }
}
