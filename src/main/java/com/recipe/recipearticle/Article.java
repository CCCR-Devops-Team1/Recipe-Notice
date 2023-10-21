package com.recipe.recipearticle;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
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
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<Answer> answerList = new ArrayList<>();
    public void addAnswer(Answer answer){
        answer.setArticle(this);
        getAnswerList().add(answer);
    }
    public int answerCount(){
        return this.answerList.size();
    }
}
