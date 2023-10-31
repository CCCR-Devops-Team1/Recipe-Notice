package com.recipe.recipearticle.notice.repository;

import com.recipe.recipearticle.notice.entity.Photo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findByArticleId(long id);
}
