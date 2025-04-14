package com.example.CMS.DTO;

import com.example.CMS.Common.GlobalConstants.Status;

import java.time.LocalDateTime;
import java.util.Date;

public class ArticleDto {
    private Long id;
    private String encryptedId;
    private String title;
    private String summary;
    private String content;
    private String authorName;
    private int status;
    private LocalDateTime createdAt;
    // Constructor
    public ArticleDto(Long id, String title, String summary, String content, String authorName, String encryptedId, Status status, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.status = status.ordinal();
        this.summary = summary;
        this.createdAt = createdAt;
        this.content = content;
        this.authorName = authorName;
        this.encryptedId = encryptedId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEncryptedId() { return encryptedId; }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setEncryptedId(String encryptedId) { this.encryptedId = encryptedId; }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }
}
