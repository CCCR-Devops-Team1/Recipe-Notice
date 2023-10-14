package com.recipe.recipearticle;

import com.recipe.recipearticle.Dto.ArticleDto;
import com.recipe.recipearticle.Dto.MemberDto;
import com.recipe.recipearticle.Dto.Response.ResponseDto;
import com.recipe.recipearticle.Dto.Response.ResponseStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class NoticeController {
    private final RestTemplateService restTemplateService;
    private final ArticleService articleService;
    // read
    @GetMapping("/notice")
    public ResponseDto createArticle(){
//        articleService.create(articleDto);
        return new ResponseDto(ResponseStatus.SUCCESS);
    }
    // create
    @PostMapping("/notice")
    public ResponseDto getAccount(HttpServletRequest request, Principal principal){
        System.out.println("사용자 이름 : " + principal.getName());
        return new ResponseDto(restTemplateService.getMemberId(request));
    }
}
