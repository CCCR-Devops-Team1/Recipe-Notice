package com.recipe.recipearticle;

import com.recipe.recipearticle.Dto.ArticleDto;
import com.recipe.recipearticle.Exception.BaseException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.recipe.recipearticle.Dto.Response.ResponseStatus.*;

@Service
@RequiredArgsConstructor
public class ArticleService {
  private final ArticleRepository articleRepository;
  private final AnswerRepository answerRepository;

  public void create(ArticleDto articleDto, long memberId) {
    Article article = Article.builder()
        .subject(articleDto.getSubject())
        .content(articleDto.getContent())
        .memberId(memberId)
        .build();
    articleRepository.save(article);
  }
  public Article getArticle(long article_id) {
    Article article = articleRepository.findById(article_id).orElseThrow(() ->
        new BaseException(NO_ARTICLE));
    System.out.println(article.getAnswerList());
    List<Answer> answerList = answerRepository.findByArticleId(article.getId());
    System.out.println(answerList);
    article.setAnswerList(answerList);
    return article;
  }

  public Article modify(ArticleDto articleDto, long member_id) {
    System.out.println(articleDto.getId()+" / "+member_id);
    Article article = articleRepository.findByIdAndMemberId(articleDto.getId(), member_id).orElseThrow(
        () ->  new BaseException(NO_UPDATE_AUTHORITY)
    );
    article.setSubject(articleDto.getSubject());
    article.setContent(articleDto.getContent());
    articleRepository.save(article);

    return article;
  }
  @Transactional
  public void delete(long id, long memberId) {
      Article article = articleRepository.findByIdAndMemberId(id, memberId).orElseThrow(
          () -> new BaseException(NO_DELETE_AUTHORITY)
      );
      articleRepository.delete(article);
  }
}
