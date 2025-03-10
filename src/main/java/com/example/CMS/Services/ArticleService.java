package com.example.CMS.Services;

import com.example.CMS.Model.Article;
import com.example.CMS.Repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        articleRepository.save(article);
    }
}
