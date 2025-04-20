package com.example.CMS.Common;

import com.example.CMS.Model.Article;
import com.example.CMS.Model.Views;
import com.example.CMS.Repository.ViewsRepository;

import java.time.LocalDate;
import java.util.Optional;

public class IncreaseViews {
    private ViewsRepository viewsRepository;
    public IncreaseViews(ViewsRepository viewsRepository){
        this.viewsRepository = viewsRepository;
    }
    public void increaseViews(Article article){
        Optional<Views> views = viewsRepository.getByArticleAndDate(article,LocalDate.now());
        if(views.isPresent()){
            views.get().setArticle(article);
            views.get().setNumberOfViews(views.get().getNumberOfViews()+1L);
            viewsRepository.save(views.get());
        }else{
            Views newViews = new Views();
            newViews.setDate(LocalDate.now());
            newViews.setNumberOfViews(1L);
            newViews.setArticle(article);
            viewsRepository.save(newViews);
        }
    }

}
