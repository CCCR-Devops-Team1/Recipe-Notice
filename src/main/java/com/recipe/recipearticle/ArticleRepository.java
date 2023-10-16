package com.recipe.recipearticle;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
  Optional<Article> findByIdAndMemberId(long id, long memberId);

  void deleteByIdAndMemberId(long id, long memberId);
}
