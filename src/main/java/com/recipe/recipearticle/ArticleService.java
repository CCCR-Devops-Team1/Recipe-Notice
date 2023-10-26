package com.recipe.recipearticle;

import static com.recipe.recipearticle.Dto.Response.ResponseStatus.NO_ARTICLE;
import static com.recipe.recipearticle.Dto.Response.ResponseStatus.NO_DELETE_AUTHORITY;
import static com.recipe.recipearticle.Dto.Response.ResponseStatus.NO_UPDATE_AUTHORITY;

import com.recipe.recipearticle.Dto.ArticleDto;
import com.recipe.recipearticle.Exception.BaseException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
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

    @Transactional
    public Article create(ArticleDto articleDto, long memberId) {
        Article article = null;
        if (!(articleDto.getPhotoList() == null)) {
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

    public Article getArticle2(long article_id) {
        Article article = articleRepository.findById(article_id).orElseThrow(() ->
                new BaseException(NO_ARTICLE));
        System.out.println(article.getAnswerList());
        List<Answer> answerList = answerRepository.findByArticleId(article.getId());
        article.setAnswerList(answerList);
        List<Photo> photoList = photoRepository.findByArticleId(article.getId());
        article.setPhotoList(photoList);
        return article;
    }

    public Article modify(ArticleDto articleDto, long member_id) {
        System.out.println(articleDto.getId() + " / " + member_id);
        Article article = articleRepository.findByIdAndMemberId(articleDto.getId(), member_id).orElseThrow(
                () -> new BaseException(NO_UPDATE_AUTHORITY)
        );
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
