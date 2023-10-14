package com.recipe.recipearticle;

import com.recipe.recipearticle.Dto.ArticleDto;
import com.recipe.recipearticle.Dto.MemberDto;
import com.recipe.recipearticle.Dto.Response.ResponseDto;
import com.recipe.recipearticle.Dto.Response.ResponseStatus;
import com.recipe.recipearticle.Util.Validation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class NoticeController {
    private final RestTemplateService restTemplateService;
    private final ArticleService articleService;
    private final ArticleRepository articleRepository;
    // read
    @GetMapping("/notice")
    public ResponseDto createArticle(){
        return new ResponseDto(articleRepository.findById(1L).get());
    }
    // article create
    @PostMapping("/notice")
    public ResponseDto getAccount(HttpServletRequest request, Principal principal, @Valid ArticleDto articleDto, BindingResult bindingResult){
        System.out.println("사용자 이름 : " + principal.getName());
        long member_id = restTemplateService.getMemberId(request).getResult().getId();

        List<String> error_list = Validation.getValidationError(bindingResult);

        if(!error_list.isEmpty()){
            return new ResponseDto(false, HttpStatus.BAD_REQUEST.value(), error_list);
        }else{
            articleService.create(articleDto, member_id);
            return new ResponseDto(ResponseStatus.SIGNUP_SUCCESS);
        }
    }

}
