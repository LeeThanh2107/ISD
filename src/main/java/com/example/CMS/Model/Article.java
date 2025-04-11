package com.example.CMS.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;

@Entity
@Table(name = "Article")

public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ArticleID;

    @Column(nullable = false)
    private String Title;

    @Column(nullable = false)
    private String Abstract;

    @Column(nullable = false, columnDefinition = "MEDIUMTEXT")
    private String Content;

    @Column(nullable = false)
    @Min(value = 0)
    @Max(value = 4)
    private int status;
    @Column
    private LocalDateTime CreatedAt;
    @Column
    private LocalDateTime UpdatedAt;

    @ManyToOne
    @JoinColumn(name="UserId", nullable = false)
    private User user;

    public Long getArticleID() {
        return ArticleID;
    }

    public void setArticleID(Long articleID) {
        ArticleID = articleID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAbstract() {
        return Abstract;
    }

    public void setAbstract(String anAbstract) {
        Abstract = anAbstract;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public int getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return CreatedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return UpdatedAt;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        CreatedAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        UpdatedAt = updatedAt;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
