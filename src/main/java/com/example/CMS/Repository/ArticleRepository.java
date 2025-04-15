package com.example.CMS.Repository;

import com.example.CMS.Common.GlobalConstants.Status;
import com.example.CMS.Model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {
    public List<Article> getByStatus(Status status);
}
