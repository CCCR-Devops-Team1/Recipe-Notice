package com.recipe.recipearticle;

import com.recipe.recipearticle.Dto.Response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NoticeController {
    private final RestTemplateService restTemplateService;
    @GetMapping("/getname")
    public String getAccount(){
        return restTemplateService.getName();
    }
    @PostMapping("/notice")
    public ResponseDto createArticle(){
        return null;
    }
}
