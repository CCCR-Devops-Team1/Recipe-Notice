package com.recipe.recipearticle.notice.repository;

import com.recipe.recipearticle.notice.entity.Article;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Optional<Article> findByIdAndMemberId(long id, long memberId);

    void deleteByIdAndMemberId(long id, long memberId);

    Page<Article> findAll(Pageable pageable);
}
