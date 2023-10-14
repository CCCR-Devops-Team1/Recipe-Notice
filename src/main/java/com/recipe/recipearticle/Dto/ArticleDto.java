package com.recipe.recipearticle.Dto;


import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {
  @NotBlank(message = "제목은 필수 입력 사항입니다.")
  private String subject;
  @NotBlank(message = "내용은 필수 입력 사항입니다.")
  private String content;
}
