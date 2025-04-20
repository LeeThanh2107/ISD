// Article.java
package com.example.CMS.Model;

import com.example.CMS.Common.GlobalConstants.Status;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime UpdatedAt;

    @Column
    private LocalDateTime sendToEditorAt;

    @Column
    private LocalDateTime publishedAt;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Views> views = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "writer_id", nullable = false)
    private User writer;

    @ManyToOne
    @JoinColumn(name = "editor_id", nullable = true)
    private User editor;

    public LocalDateTime getSendToEditorAt() {
        return sendToEditorAt;
    }

    public void setSendToEditorAt(LocalDateTime sendToEditorAt) {
        this.sendToEditorAt = sendToEditorAt;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

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

    public List<Views> getViews() {
        return views;
    }

    public void setViews(List<Views> views) {
        this.views = views;
    }

    // Helper method to add a view record
    public void addView(Views view) {
        views.add(view);
        view.setArticle(this);
    }

    // Helper method to remove a view record
    public void removeView(Views view) {
        views.remove(view);
        view.setArticle(null);
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

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return UpdatedAt;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        UpdatedAt = updatedAt;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public User getEditor() {
        return editor;
    }

    public void setEditor(User editor) {
        this.editor = editor;
    }
}