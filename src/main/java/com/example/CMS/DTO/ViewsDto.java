
// ViewsDto.java
package com.example.CMS.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ViewsDto {
    private Long id;
    private Long numberOfViews;
    private LocalDate date;
    private Long articleId;
    private String articleEncryptedId;
    private String articleName;
    private String authorName;
    private String editorName;

    // Default constructor
    public ViewsDto() {
    }

    // Constructor with all fields
    public ViewsDto(Long id, Long numberOfViews, LocalDate date, Long articleId,
                    String articleEncryptedId, String articleName, String authorName,
                    String editorName) {
        this.id = id;
        this.numberOfViews = numberOfViews;
        this.date = date;
        this.articleId = articleId;
        this.articleEncryptedId = articleEncryptedId;
        this.articleName = articleName;
        this.authorName = authorName;
        this.editorName = editorName;
    }

    // Constructor with essential fields
    public ViewsDto(Long id, Long numberOfViews, LocalDate date, Long articleId) {
        this.id = id;
        this.numberOfViews = numberOfViews;
        this.date = date;
        this.articleId = articleId;
    }

    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }

    // Builder class
    public static class Builder {
        private final ViewsDto dto;

        public Builder() {
            dto = new ViewsDto();
        }

        public Builder id(Long id) {
            dto.id = id;
            return this;
        }

        public Builder numberOfViews(Long numberOfViews) {
            dto.numberOfViews = numberOfViews;
            return this;
        }

        public Builder date(LocalDate date) {
            dto.date = date;
            return this;
        }

        public Builder articleId(Long articleId) {
            dto.articleId = articleId;
            return this;
        }

        public Builder articleEncryptedId(String articleEncryptedId) {
            dto.articleEncryptedId = articleEncryptedId;
            return this;
        }

        public Builder articleName(String articleName) {
            dto.articleName = articleName;
            return this;
        }

        public Builder authorName(String authorName) {
            dto.authorName = authorName;
            return this;
        }

        public Builder editorName(String editorName) {
            dto.editorName = editorName;
            return this;
        }

        public ViewsDto build() {
            return dto;
        }
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumberOfViews() {
        return numberOfViews;
    }

    public void setNumberOfViews(Long numberOfViews) {
        this.numberOfViews = numberOfViews;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getArticleEncryptedId() {
        return articleEncryptedId;
    }

    public void setArticleEncryptedId(String articleEncryptedId) {
        this.articleEncryptedId = articleEncryptedId;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getEditorName() {
        return editorName;
    }

    public void setEditorName(String editorName) {
        this.editorName = editorName;
    }

    // For debugging
    @Override
    public String toString() {
        return "ViewsDto{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", articleName='" + articleName + '\'' +
                ", numberOfViews=" + numberOfViews +
                ", date=" + date +
                '}';
    }
}