package com.recipe.recipearticle.notice.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String subject;
    @Lob
    private String content;
    @NotNull
    private long memberId;
    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime updateDate;

    public Article(final String subject,
                   final String content,
                   final long memberId,
                   final List<Photo> photos) {
        this.subject = subject;
        this.content = content;
        this.memberId = memberId;
        this.photoList = new ArrayList<>();
        addPhotos(photos);
    }

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @Default
    private List<Photo> photoList = new ArrayList<>();

    private void addPhotos(final List<Photo> addedPhotos) {
        addedPhotos.forEach(addedPhoto -> {
            addedPhoto.initArticle(this);
            getPhotoList().add(addedPhoto);
        });
    }

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @JsonManagedReference
    @Default
    private List<Answer> answerList = new ArrayList<>();

    public void addAnswer(Answer answer) {
        answer.setArticle(this);
        getAnswerList().add(answer);
    }

    public int answerCount() {
        return this.answerList.size();
    }
}
