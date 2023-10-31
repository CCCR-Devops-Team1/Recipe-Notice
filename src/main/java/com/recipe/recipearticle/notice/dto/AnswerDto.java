package com.recipe.recipearticle.notice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDto {
    private long id;
    @NotBlank(message = "내용은 필수 입력 사항입니다.")
    private String content;
}