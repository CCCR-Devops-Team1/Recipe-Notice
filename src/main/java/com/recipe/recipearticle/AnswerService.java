package com.recipe.recipearticle;

import com.recipe.recipearticle.Dto.AnswerDto;
import com.recipe.recipearticle.Exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.recipe.recipearticle.Dto.Response.ResponseStatus.NO_ARTICLE;
import static com.recipe.recipearticle.Dto.Response.ResponseStatus.NO_DELETE_AUTHORITY;

@Service
@RequiredArgsConstructor
public class AnswerService {
  private final AnswerRepository answerRepository;
  private final ArticleRepository articleRepository;
  public void create(long memberId, long article_id, AnswerDto answerDto) {
    Article article = articleRepository.findById(article_id).orElseThrow( () ->
            new BaseException(NO_ARTICLE)
        );
    Answer answer = Answer.builder()
        .author("s").build();
  }
}
