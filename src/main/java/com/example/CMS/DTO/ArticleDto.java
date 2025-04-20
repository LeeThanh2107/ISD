package com.example.CMS.DTO;

import com.example.CMS.Common.GlobalConstants.Status;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleDto {
    private Long id;
    private String encryptedId;
    private String title;
    private String summary;
    private String content;
    private String authorName;
    private int status;
    private LocalDateTime createdAt;
    private LocalDateTime sendToEditorAt;
    private LocalDateTime updatedAt;
    private LocalDateTime publishedAt;
    private String editorName;
    private List<ViewsDto> viewsList; // ✅ New field

    // Default constructor
    public ArticleDto() {
    }

    // Constructor with all fields
    public ArticleDto(Long id, String title, String summary, String content, String authorName,
                      String encryptedId, Status status, LocalDateTime sendToEditorAt,
                      LocalDateTime updatedAt, LocalDateTime createdAt, Long views,
                      LocalDateTime publishedAt, String editorName, List<ViewsDto> viewsList) {
        this.id = id;
        this.title = title;
        this.status = status.ordinal();
        this.summary = summary;
        this.createdAt = createdAt;
        this.content = content;
        this.authorName = authorName;
        this.encryptedId = encryptedId;
        this.sendToEditorAt = sendToEditorAt;
        this.updatedAt = updatedAt;
        this.publishedAt = publishedAt;
        this.editorName = editorName;
        this.viewsList = viewsList;
    }

    // Constructor with essential fields
    public ArticleDto(Long id, String title, String summary, Status status, String authorName) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.status = status.ordinal();
        this.authorName = authorName;
    }

    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final ArticleDto dto;

        public Builder() {
            dto = new ArticleDto();
        }

        public Builder id(Long id) {
            dto.id = id;
            return this;
        }

        public Builder encryptedId(String encryptedId) {
            dto.encryptedId = encryptedId;
            return this;
        }

        public Builder title(String title) {
            dto.title = title;
            return this;
        }

        public Builder summary(String summary) {
            dto.summary = summary;
            return this;
        }

        public Builder content(String content) {
            dto.content = content;
            return this;
        }

        public Builder authorName(String authorName) {
            dto.authorName = authorName;
            return this;
        }

        public Builder status(Status status) {
            dto.status = status.ordinal();
            return this;
        }

        public Builder statusOrdinal(int status) {
            dto.status = status;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            dto.createdAt = createdAt;
            return this;
        }


        public Builder sendToEditorAt(LocalDateTime sendToEditorAt) {
            dto.sendToEditorAt = sendToEditorAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            dto.updatedAt = updatedAt;
            return this;
        }

        public Builder publishedAt(LocalDateTime publishedAt) {
            dto.publishedAt = publishedAt;
            return this;
        }

        public Builder editorName(String editorName) {
            dto.editorName = editorName;
            return this;
        }

        public Builder viewsList(List<ViewsDto> viewsList) {
            dto.viewsList = viewsList;
            return this;
        }

        public ArticleDto build() {
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

    public String getEncryptedId() {
        return encryptedId;
    }

    public void setEncryptedId(String encryptedId) {
        this.encryptedId = encryptedId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setStatus(Status status) {
        this.status = status.ordinal();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getSendToEditorAt() {
        return sendToEditorAt;
    }

    public void setSendToEditorAt(LocalDateTime sendToEditorAt) {
        this.sendToEditorAt = sendToEditorAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getEditorName() {
        return editorName;
    }

    public void setEditorName(String editorName) {
        this.editorName = editorName;
    }

    public List<ViewsDto> getViewsList() {
        return viewsList;
    }

    public void setViewsList(List<ViewsDto> viewsList) {
        this.viewsList = viewsList;
    }

    @Override
    public String toString() {
        return "ArticleDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", authorName='" + authorName + '\'' +
                ", viewsList=" + viewsList +
                '}';
    }
}
