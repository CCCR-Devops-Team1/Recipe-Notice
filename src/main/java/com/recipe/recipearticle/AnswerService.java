package com.recipe.recipearticle;

import com.recipe.recipearticle.Dto.AnswerDto;
import com.recipe.recipearticle.Exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.recipe.recipearticle.Dto.Response.ResponseStatus.*;

@Service
@RequiredArgsConstructor
public class AnswerService {
  private final AnswerRepository answerRepository;
  private final ArticleRepository articleRepository;
  private final MemberRepository memberRepository;
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

  public Answer modifyAnswer(AnswerDto answerDto, long article_id, long member_id) {
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
  @Transactional
  public void deleteAnswer(long member_id, long article_id) {
    Article article = articleRepository.findById(article_id).orElseThrow(() ->
      new BaseException(NO_ARTICLE));

    if(article.getMemberId() != member_id){
      new BaseException(NO_DELETE_AUTHORITY);
    }
    Answer answer = answerRepository.findByArticleIdAndMemberId(article_id, member_id).orElseThrow(() ->
      new BaseException(NO_ANSWER));
    answerRepository.delete(answer);
    answerRepository.save(answer);
  }
}
