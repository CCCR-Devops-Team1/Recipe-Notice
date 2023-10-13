package com.recipe.recipearticle.Dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    @NotBlank(message="account는 필수 입력 사항입니다.")
    private long id;
    private String account;
    private String pw;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
