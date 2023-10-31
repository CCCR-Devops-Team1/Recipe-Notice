package com.recipe.recipearticle.notice.service;

import static com.recipe.recipearticle.global.dto.response.ResponseStatus.NO_ANSWER;
import static com.recipe.recipearticle.global.dto.response.ResponseStatus.NO_ARTICLE;
import static com.recipe.recipearticle.global.dto.response.ResponseStatus.NO_DELETE_AUTHORITY;
import static com.recipe.recipearticle.global.dto.response.ResponseStatus.NO_UPDATE_AUTHORITY;

import com.recipe.recipearticle.global.exeption.BaseException;
import com.recipe.recipearticle.member.repository.MemberRepository;
import com.recipe.recipearticle.notice.dto.AnswerDto;
import com.recipe.recipearticle.notice.entity.Answer;
import com.recipe.recipearticle.notice.entity.Article;
import com.recipe.recipearticle.notice.repository.AnswerRepository;
import com.recipe.recipearticle.notice.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    public void createAnswer(long memberId, long article_id, AnswerDto answerDto) {
        Article article = articleRepository.findById(article_id).orElseThrow(() ->
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
        if (answer.getMemberId() != member_id) {
            new BaseException(NO_UPDATE_AUTHORITY);
        }
        answer.setContent(answerDto.getContent());
        answerRepository.save(answer);

        return answer;
    }

    @Transactional
    public void deleteAnswer(AnswerDto answerDto, long member_id, long article_id) {
        Answer answer = answerRepository.findByIdAndArticleId(answerDto.getId(), article_id).orElseThrow(
                () -> new BaseException(NO_ANSWER)
        );
        if (answer.getMemberId() != member_id) {
            new BaseException(NO_DELETE_AUTHORITY);
        }
        answerRepository.delete(answer);
    }
}
