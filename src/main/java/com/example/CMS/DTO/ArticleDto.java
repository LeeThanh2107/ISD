package com.example.CMS.DTO;

public class ArticleDto {
    private Long id;
    private String title;
    private String summary;
    private String content;
    private String authorName;

    // Constructors
    public ArticleDto() {}

    public ArticleDto(Long id, String title, String summary, String content, String authorName) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.authorName = authorName;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }
}