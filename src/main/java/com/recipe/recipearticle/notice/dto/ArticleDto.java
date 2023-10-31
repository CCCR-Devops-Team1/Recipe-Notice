package com.recipe.recipearticle.notice.dto;


import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ArticleDto {
    private long id;
    @NotBlank(message = "제목은 필수 입력 사항입니다.")
    private String subject;
    @NotBlank(message = "내용은 필수 입력 사항입니다.")
    private String content;
    private List<MultipartFile> photoList;
}
