package com.recipe.recipearticle;

import com.recipe.recipearticle.Dto.AnswerDto;
import com.recipe.recipearticle.Dto.ArticleDto;
import com.recipe.recipearticle.Dto.Response.ResponseDto;
import com.recipe.recipearticle.Dto.Response.ResponseStatus;
import com.recipe.recipearticle.Util.Validation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NoticeController {
    private final RestTemplateService restTemplateService;
    private final ArticleService articleService;
    private final ArticleRepository articleRepository;
    private final AnswerService answerService;
    private final FileService fileService;

    // All Article Read
    @GetMapping("/notice")
    public ResponseDto getArticles(Optional<Integer> pageNum) {
        System.out.println(pageNum);
        Page<Article> articles = articleService.findAll(pageNum.isPresent() ? pageNum.get() - 1 : 0);
        return new ResponseDto(articles.getContent());
    }

    @GetMapping("/notice/{article_id}")
    public ResponseDto getArticle(@PathVariable long article_id) {
        Article article = articleService.getArticle(article_id);
        return new ResponseDto(article);
    }

    // Article Create
    @PostMapping("/notice")
    public ResponseDto createArticle(HttpServletRequest request, Principal principal,
                                     @Valid @ModelAttribute ArticleDto articleDto, BindingResult bindingResult) {
        System.out.println("사용자 이름 : " + principal.getName());
        long member_id = restTemplateService.getMemberId(request).getResult().getId();

        List<String> error_list = Validation.getValidationError(bindingResult);

        if (!error_list.isEmpty()) {
            return new ResponseDto(false, HttpStatus.BAD_REQUEST.value(), error_list);
        } else {
            articleService.create(articleDto, member_id);
            return new ResponseDto(ResponseStatus.NOTICE_CREATE_SUCCESS);
        }
    }

    // Article Modify
    @PutMapping("/notice")
    public ResponseDto modifyArticle(HttpServletRequest request, Principal principal,
                                     @Valid @ModelAttribute ArticleDto articleDto, BindingResult bindingResult) {
        long member_id = restTemplateService.getMemberId(request).getResult().getId();
        List<String> error_list = Validation.getValidationError(bindingResult);
        if (!error_list.isEmpty()) {
            return new ResponseDto(false, HttpStatus.BAD_REQUEST.value(), error_list);
        } else {
            Article article = articleService.modify(articleDto, member_id);
            return new ResponseDto(article);
        }
    }

    // Article Delete
    @DeleteMapping("/notice")
    public ResponseDto deleteArticle(HttpServletRequest request, Principal principal,
                                     @RequestBody ArticleDto articleDto) {
        long member_id = restTemplateService.getMemberId(request).getResult().getId();
        articleService.delete(articleDto.getId(), member_id);
        return new ResponseDto(ResponseStatus.SUCCESS);
    }

    // Answer Create
    @PostMapping("/notice/{article_id}")
    public ResponseDto createAnswer(HttpServletRequest request, Principal principal, @PathVariable long article_id,
                                    @RequestBody AnswerDto answerDto) {
        long member_id = restTemplateService.getMemberId(request).getResult().getId();
        answerService.createAnswer(member_id, article_id, answerDto);
        return new ResponseDto(ResponseStatus.SUCCESS);
    }

    // Answer Update
    @PutMapping("/notice/{article_id}")
    public ResponseDto modifyAnswer(HttpServletRequest request, Principal principal, @PathVariable long article_id,
                                    @RequestBody AnswerDto answerDto) {
        long member_id = restTemplateService.getMemberId(request).getResult().getId();
        System.out.println("member_id : " + member_id);
        System.out.println("answer_id : " + answerDto.getId());
        Answer answer = answerService.modifyAnswer(answerDto, article_id, member_id);

        return new ResponseDto(answer);
    }

    // Answer Delete
    @DeleteMapping("/notice/{article_id}")
    public ResponseDto deleteAnswer(HttpServletRequest request, Principal principal, @PathVariable long article_id,
                                    @RequestBody AnswerDto answerDto) {
        long member_id = restTemplateService.getMemberId(request).getResult().getId();
        answerService.deleteAnswer(answerDto, member_id, article_id);
        return new ResponseDto(ResponseStatus.SUCCESS);
    }

    // 이미지 불러오기
    @GetMapping("/notice/images/{image_name}")
    public ResponseEntity<Resource> getImage(@PathVariable String image_name) {
        return fileService.read(image_name);
    }
}
