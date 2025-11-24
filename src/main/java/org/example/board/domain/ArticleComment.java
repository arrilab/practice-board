package org.example.board.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy"),
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class ArticleComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // 식별자

    @Setter
    @ManyToOne(optional = false)
    private Article article;            // 게시글

    @Setter
    @Column(nullable = false, length = 500)
    private String content;             // 본문

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;    // 생성일자

    @Column(nullable = false, length = 100)
    @CreatedBy
    private String createdBy;           // 생성자

    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime modifiedAt;   // 수정일자

    @Column(nullable = false, length = 100)
    @LastModifiedBy
    private String modifiedBy;          // 수정자

    protected ArticleComment() {
    }

    public ArticleComment(Article article, String content) {
        this.article = article;
        this.content = content;
    }

    public static ArticleComment of(Article article, String content) {
        return new ArticleComment(article, content);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ArticleComment that = (ArticleComment) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
