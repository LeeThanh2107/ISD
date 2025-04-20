package com.example.CMS.Repository;

import com.example.CMS.Model.Article;
import com.example.CMS.Model.Views;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ViewsRepository extends JpaRepository<Views,Long> {
    public Optional<Views> getByArticleAndDate(Article article, LocalDate localDate);
}
