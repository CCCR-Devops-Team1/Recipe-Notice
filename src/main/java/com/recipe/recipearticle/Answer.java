package com.recipe.recipearticle;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Answer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String author;
  @Column(length = 200)
  private String content;
  @CreatedDate
  private LocalDateTime createDate;
  @LastModifiedDate
  private LocalDateTime updateDate;
  @ManyToOne
  private Article article;
}