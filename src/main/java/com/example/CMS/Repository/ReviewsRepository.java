package com.example.CMS.Repository;

import com.example.CMS.Model.Article;
import com.example.CMS.Model.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews,Long> {
    List<Reviews> findByArticle(Article article);
}
