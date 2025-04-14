package com.example.CMS.Services;

import com.example.CMS.Common.GlobalConstants.Status;
import com.example.CMS.Common.IdEncryptor;
import com.example.CMS.DTO.ArticleDto;
import com.example.CMS.Model.Article;
import com.example.CMS.Model.CustomUserDetails;
import com.example.CMS.Model.User;
import com.example.CMS.Repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    public List<ArticleDto> getAllArticles() {
        List<Article> articles = articleRepository.findAll();

        return articles.stream().map(article -> {
            try {
                return new ArticleDto(
                        article.getArticleID(),
                        article.getTitle(),
                        article.getAbstract(),
                        article.getContent(),
                        article.getUser() != null ? article.getUser().getName() : null,
                        IdEncryptor.encrypt(article.getArticleID()),
                        article.getStatus(),
                        article.getCreatedAt()
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }


    public ArticleDto detail(String id) throws Exception {
        Long articleId = IdEncryptor.decrypt(id);
        Article article = articleRepository.getReferenceById(articleId);
        return new ArticleDto(
                article.getArticleID(),
                article.getTitle(),
                article.getAbstract(),
                article.getContent(),
                article.getUser() != null ? article.getUser().getName() : null,
                IdEncryptor.encrypt((article.getArticleID())),
                article.getStatus(),
                article.getCreatedAt()
        );
    }
    public void update(Article article){
        articleRepository.save(article);
    }
    public void delete(Long id){
        articleRepository.deleteById(id);
    }
    public void create(Article article){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails) {
                User user = ((CustomUserDetails) principal).getUser(); // Giả sử getUser() trả về User
                article.setUser(user);
            }
        } else {
            throw new SecurityException("No authenticated user found");
        }
        article.setCreatedAt(LocalDateTime.now());
        article.setStatus(Status.DRAFT);
        articleRepository.save(article);
    }
}
