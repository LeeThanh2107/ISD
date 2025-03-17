package com.example.CMS.Services;

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

@Service
public class ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    public List<Article> index(){
        return articleRepository.findAll();
    }

    public Article detail(Long id){
        return articleRepository.getReferenceById(id);
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
        article.setStatus(1);
        articleRepository.save(article);
    }
}
