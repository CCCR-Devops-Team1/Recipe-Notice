package com.recipe.recipearticle.notice.service;

import static com.recipe.recipearticle.global.dto.response.ResponseStatus.NO_ARTICLE;
import static com.recipe.recipearticle.global.dto.response.ResponseStatus.NO_DELETE_AUTHORITY;
import static com.recipe.recipearticle.global.dto.response.ResponseStatus.NO_UPDATE_AUTHORITY;

import com.recipe.recipearticle.global.exeption.BaseException;
import com.recipe.recipearticle.notice.dto.ArticleDto;
import com.recipe.recipearticle.notice.entity.Answer;
import com.recipe.recipearticle.notice.entity.Article;
import com.recipe.recipearticle.notice.entity.Photo;
import com.recipe.recipearticle.notice.repository.AnswerRepository;
import com.recipe.recipearticle.notice.repository.ArticleRepository;
import com.recipe.recipearticle.notice.repository.PhotoRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final AnswerRepository answerRepository;
    private final FileService fileService;
    private final PhotoRepository photoRepository;

    private void uploadImages(List<Photo> photoList, List<MultipartFile> fileImages) {
        IntStream.range(0, photoList.size())
                .forEach(i -> fileService.upload(fileImages.get(i), photoList.get(i).getUniqueName()));
    }

    public Page<Article> findAll(Integer current_page) {
        List<Sort.Order> sort = new ArrayList<>();
        sort.add(Sort.Order.desc("createDate"));
        sort.add(Sort.Order.desc("id"));
        Page<Article> articles = articleRepository.findAll(PageRequest.of(current_page, 10, Sort.by(sort)));
        return articles;
    }

    @Transactional
    public Article create(ArticleDto articleDto, long memberId) {
        Article article = null;
        if (articleDto.getPhotoList() != null) {
            System.out.println("실행");
            List<Photo> photoList = articleDto.getPhotoList().stream().map(i -> new Photo(i.getOriginalFilename()))
                    .collect(Collectors.toList());
            article = new Article(articleDto.getSubject(), articleDto.getContent(), memberId, photoList);
            uploadImages(article.getPhotoList(), articleDto.getPhotoList());
        } else {
            article = Article.builder()
                    .subject(articleDto.getSubject())
                    .content(articleDto.getContent())
                    .memberId(memberId)
                    .build();
        }
        article = articleRepository.save(article);
        return article;
    }

    public Article getArticle(long article_id) {
        Article article = articleRepository.findById(article_id).orElseThrow(() ->
                new BaseException(NO_ARTICLE));
        System.out.println(article.getAnswerList());
        List<Answer> answerList = answerRepository.findByArticleId(article.getId());
        System.out.println(answerList);
        article.setAnswerList(answerList);
        return article;
    }

    public Article modify(ArticleDto articleDto, long member_id) {
        System.out.println(articleDto.getId() + " / " + member_id);
        Article article = articleRepository.findByIdAndMemberId(articleDto.getId(), member_id).orElseThrow(
                () -> new BaseException(NO_UPDATE_AUTHORITY)
        );
        if (articleDto.getPhotoList() != null) {
            // 변경할 이미지 리스트
            List<Photo> photoList = articleDto.getPhotoList().stream().map(i -> new Photo(i.getOriginalFilename()))
                    .collect(Collectors.toList());
            // 기존의 이미지 리스트
            List<Photo> originPhotoList = photoRepository.findByArticleId(article.getId());
            // 기존의 이미지 로컬에서 삭제
            originPhotoList.stream().forEach(i -> fileService.delete(i.getUniqueName()));
            // 기존의 이미지 DB에서 삭제
            originPhotoList.stream().forEach(i -> photoRepository.delete(i));
            // 변경할 이미지들의 many to one 설정
            photoList.stream().forEach(e -> e.setArticle(article));
            // 게시글의 one to many 설정
            article.setPhotoList(photoList);
            uploadImages(article.getPhotoList(), articleDto.getPhotoList());
        }

        article.setSubject(articleDto.getSubject());
        article.setContent(articleDto.getContent());
        articleRepository.save(article);

        return article;
    }

    @Transactional
    public void delete(long id, long memberId) {
        Article article = articleRepository.findByIdAndMemberId(id, memberId).orElseThrow(
                () -> new BaseException(NO_DELETE_AUTHORITY)
        );
        List<Photo> photoList = photoRepository.findByArticleId(article.getId());
        photoList.stream().forEach(i -> fileService.delete(i.getUniqueName()));
        articleRepository.delete(article);
    }
}
