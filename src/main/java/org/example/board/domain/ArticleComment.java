package org.example.board.domain;

import java.time.LocalDateTime;

public class ArticleComment {
    private Long id;                    // 식별자
    private Article article;            // 게시글
    private String content;             // 본문

    private LocalDateTime createdAt;    // 생성일자
    private String createdBy;           // 생성자
    private LocalDateTime modifiedAt;   // 수정일자
    private String modifiedBy;          // 수정자
}
