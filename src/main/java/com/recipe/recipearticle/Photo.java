package com.recipe.recipearticle;

import static com.recipe.recipearticle.Dto.Response.ResponseStatus.UNSUPPORT_IMAGE;

import com.recipe.recipearticle.Exception.BaseException;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class Photo {
    private final static List<String> supportedExtensions = List.of("jpg", "jpeg", "gif", "bmp", "png");
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;
    private String uniqueName;
    private String originFileName;

    public Photo(final String originFileName) {
        this.originFileName = originFileName;
        this.uniqueName = generateUniqueName(extractExtension(originFileName));
    }

    public void setArticle(Article article) {
        this.article = article;
        if (!article.getPhotoList().contains(this)) {
            article.getPhotoList().add(this);
        }
    }

    private String generateUniqueName(final String extension) {
        return UUID.randomUUID() + "." + extension;
    }

    private String extractExtension(final String originName) {
        String extension = originName.substring(originName.lastIndexOf(".") + 1);

        if (isSupportedFormat(extension)) {
            return extension;
        }

        throw new BaseException(UNSUPPORT_IMAGE);
    }

    private boolean isSupportedFormat(final String extension) {
        return supportedExtensions.stream()
                .anyMatch(supportedExtension -> supportedExtension.equalsIgnoreCase(extension));
    }

    public boolean isSameImageId(final int id) {
        return this.getId() == id;
    }

    public void initArticle(final Article article) {
        if (this.article == null) {
            this.article = article;
        }
    }
}
