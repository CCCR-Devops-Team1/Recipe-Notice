package com.recipe.recipearticle;

import com.recipe.recipearticle.Dto.AnswerDto;
import com.recipe.recipearticle.Exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.recipe.recipearticle.Dto.Response.ResponseStatus.*;

@Service
@RequiredArgsConstructor
public class AnswerService {
  private final AnswerRepository answerRepository;
  private final ArticleRepository articleRepository;
  public void createAnswer(long memberId, long article_id, AnswerDto answerDto) {
    Article article = articleRepository.findById(article_id).orElseThrow( () ->
            new BaseException(NO_ARTICLE)
        );
    Answer answer = Answer.builder()
        .content(answerDto.getContent())
        .article(article)
        .memberId(memberId).build();

    article.getAnswerList().add(answer);
    System.out.println(article.getAnswerList());
    articleRepository.save(article);
  }

  public Answer modify(AnswerDto answerDto, long article_id, long member_id) {
    Answer answer = answerRepository.findByIdAndArticleId(answerDto.getId(), article_id).orElseThrow(
        () -> new BaseException(NO_ANSWER)
    );
    if(answer.getMemberId() != member_id){
      new BaseException(NO_UPDATE_AUTHORITY);
    }
    answer.setContent(answerDto.getContent());
    answerRepository.save(answer);

    return answer;
  }
}
