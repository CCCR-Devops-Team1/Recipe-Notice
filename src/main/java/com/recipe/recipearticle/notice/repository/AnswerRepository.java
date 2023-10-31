package com.recipe.recipearticle.notice.repository;

import com.recipe.recipearticle.notice.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByArticleId(long id);

    Optional<Answer> findByIdAndArticleId(long id, long article_id);

    Optional<Answer> findByArticleIdAndMemberId(long article_id, long member_id);
}
