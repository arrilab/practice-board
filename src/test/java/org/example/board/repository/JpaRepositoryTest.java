package org.example.board.repository;

import org.example.board.config.JpaConfig;
import org.example.board.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    @Autowired
    JpaRepositoryTest(ArticleRepository articleRepository, ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @DisplayName("[SELECT] 게시글 리스트 조회")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() {
        // Given

        // When
        List<Article> articles = articleRepository.findAll();

        // Then
        assertThat(articles)
                .isNotNull()
                .hasSize(123); // data.sql에 123개의 데이터가 있다고 가정한 상태
    }

    @DisplayName("[INSERT] 게시글 생성")
    @Test
    void givenArticleInfo_whenInserting_thenWorksFine() {
        // Given
        long initialCount = articleRepository.count();
        Article article = Article.of("new article", "new content", "#spring");

        // When
        Article savedArticle = articleRepository.save(article);

        // Then
        assertThat(articleRepository.count()).isEqualTo(initialCount + 1);
        assertThat(savedArticle.getTitle()).isEqualTo("new article"); // 내용도 잘 들어갔는지 체크
    }

    @DisplayName("[UPDATE] 게시글 수정 - 해시태그 업데이트")
    @Test
    void givenArticleAndModifiedInfo_whenUpdating_thenWorksFine() {
        // Given
        Article article = articleRepository.findById(1L).orElseThrow();
        String updatedHashtag = "#springboot";
        article.setHashtag(updatedHashtag);

        // When
        // saveAndFlush: 테스트 트랜잭션 내에서도 즉시 쿼리를 날려 확인하기 위함
        Article savedArticle = articleRepository.saveAndFlush(article);

        // Then
        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
    }

    @DisplayName("[DELETE] 게시글 삭제 시, 연관된 댓글도 함께 삭제됨")
    @Test
    void givenArticle_whenDeleting_thenAlsoDeletesRelatedComments() {
        // Given
        Article article = articleRepository.findById(1L).orElseThrow();

        // 게시글 삭제 전 데이터 수 확인
        long initialArticleCount = articleRepository.count();
        long initialCommentCount = articleCommentRepository.count();
        int relatedCommentSize = article.getArticleComments().size();

        // When
        articleRepository.delete(article);

        // Then
        assertThat(articleRepository.count()).isEqualTo(initialArticleCount - 1);
        assertThat(articleCommentRepository.count()).isEqualTo(initialCommentCount - relatedCommentSize);
    }
}
