package com.example.CMS.Repository;

import com.example.CMS.Common.GlobalConstants.Status;
import com.example.CMS.Model.Article;
import com.example.CMS.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {
    public List<Article> getByStatus(Status status);
    public List<Article> findAllByStatusNot(Status status);
    public List<Article> findAllByStatus(Status status);
    public List<Article> getByWriter(User user);
    public Optional<Article> findTopByWriterAndStatusOrderByCreatedAtDesc(User user, Status status);

}
