package com.example.CMS.Common;

import com.example.CMS.DTO.ArticleDto;
import com.example.CMS.DTO.ViewsDto;
import com.example.CMS.Model.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConvertToDTO {
    public static ArticleDto convertToDto(Article article) {
        try {
            List<ViewsDto> viewsDtos = article.getViews() != null
                    ? article.getViews().stream().map(view -> {
                        try {
                            return ViewsDto.builder()
                                    .id(view.getId())
                                    .numberOfViews(view.getNumberOfViews())
                                    .date(view.getDate())
                                    .articleId(article.getArticleID())
                                    .articleEncryptedId(IdEncryptor.encrypt(article.getArticleID()))
                                    .articleName(article.getTitle())
                                    .authorName(article.getWriter() != null ? article.getWriter().getName() : null)
                                    .editorName(article.getEditor() != null ? article.getEditor().getName() : null)
                                    .build();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toList())
                    : new ArrayList<>();

            return ArticleDto.builder()
                    .id(article.getArticleID())
                    .title(article.getTitle())
                    .summary(article.getAbstract())
                    .content(article.getContent())
                    .authorName(article.getWriter() != null ? article.getWriter().getName() : null)
                    .encryptedId(IdEncryptor.encrypt(article.getArticleID()))
                    .status(article.getStatus())
                    .sendToEditorAt(article.getSendToEditorAt())
                    .updatedAt(article.getUpdatedAt())
                    .createdAt(article.getCreatedAt())
                    .publishedAt(article.getPublishedAt())
                    .viewsList(viewsDtos) // ← Include views list here
                    .editorName(article.getEditor() != null ? article.getEditor().getName() : null)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Error converting Article to ArticleDto", e);
        }
    }
}

