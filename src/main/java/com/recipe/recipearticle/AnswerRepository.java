package com.recipe.recipearticle;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer,Long> {
  List<Answer> findByArticleId(long id);
}
