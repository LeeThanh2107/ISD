package com.example.CMS.Services.Writer;

import com.example.CMS.Common.ConvertToDTO;
import com.example.CMS.Common.GlobalConstants.Status;
import com.example.CMS.Common.IdEncryptor;
import com.example.CMS.Common.IncreaseViews;
import com.example.CMS.DTO.ArticleDto;
import com.example.CMS.Model.Article;
import com.example.CMS.Model.CustomUserDetails;
import com.example.CMS.Model.User;
import com.example.CMS.Repository.ArticleRepository;
import com.example.CMS.Repository.ViewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.CMS.Common.ConvertToDTO.convertToDto;

@Service
public class WriterArticleService {
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    ViewsRepository viewsRepository;
    public List<ArticleDto> getAllArticles() {
        List<Article> articles;
        User user = getUser();
        articles = articleRepository.getByWriter(user);
        return articles.stream()
                .map(ConvertToDTO::convertToDto)
                .collect(Collectors.toList());
    }

    public Map<Integer, ArticleDto> getMostRecentArticlesByStatus() {
        Map<Integer, ArticleDto> result = new HashMap<>();
        User user = getUser();

        // Get all articles for the user
        List<Article> userArticles = articleRepository.getByWriter(user);

        // Filter articles by status and get most recent ones
        Map<Status, List<Article>> articlesByStatus = userArticles.stream()
                .filter(article -> article.getStatus() == Status.PUBLISHED || article.getStatus() == Status.REQUEST_REVIEW)
                .collect(Collectors.groupingBy(Article::getStatus));

        // Get the most recent article with status REQUEST_REVIEW
        if (articlesByStatus.containsKey(Status.REQUEST_REVIEW) && !articlesByStatus.get(Status.REQUEST_REVIEW).isEmpty()) {
            Article mostRecentStatus2 = findMostRecent(articlesByStatus.get(Status.REQUEST_REVIEW));
            result.put(1, convertToDto(mostRecentStatus2));
        }

        // Get the most recent article with status PUBLISHED
        if (articlesByStatus.containsKey(Status.PUBLISHED) && !articlesByStatus.get(Status.PUBLISHED).isEmpty()) {
            Article mostRecentStatus3 = findMostRecent(articlesByStatus.get(Status.PUBLISHED));
            result.put(3, convertToDto(mostRecentStatus3));
        }

        return result;
    }


    // Helper method to find the most recent article in a list
    private Article findMostRecent(List<Article> articles) {
        return articles.stream()
                .max(Comparator.comparing(Article::getCreatedAt))
                .orElse(null);
    }

    private static User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("No authenticated user found");
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof CustomUserDetails)) {
            throw new SecurityException("Invalid authentication details");
        }

        return ((CustomUserDetails) principal).getUser();
    }

    public ArticleDto detail(String id) throws Exception {
        Long articleId = IdEncryptor.decrypt(id);
        Article article = articleRepository.getReferenceById(articleId);
        IncreaseViews increaseViews = new IncreaseViews(viewsRepository);
        increaseViews.increaseViews(article);
        return convertToDto(article);
    }

    public void update(Article article, String id) throws Exception {
        Long articleId = IdEncryptor.decrypt(id);
        Article existedArticle = articleRepository.getReferenceById(articleId);
        User user = getUser();

        existedArticle.setWriter(user);
        existedArticle.setTitle(article.getTitle());
        existedArticle.setAbstract(article.getAbstract());
        existedArticle.setContent(article.getContent());
        existedArticle.setUpdatedAt(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));

        if(article.getStatus() == Status.REQUEST_REVIEW){
            existedArticle.setStatus(Status.REQUEST_REVIEW);
        }else if(article.getStatus() == Status.DRAFT){
            existedArticle.setStatus(Status.DRAFT);
        }

        articleRepository.save(existedArticle);
    }

    public void delete(Long id){
        articleRepository.deleteById(id);
    }

    public void saveDraft(Article article){
        User user = getUser();
        article.setWriter(user);
        article.setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
        article.setStatus(Status.DRAFT);
        articleRepository.save(article);
    }

    public void sendToEditor(Article article){
        User user = getUser();
        if(article.getArticleID() != null){
            Optional<Article> draft = articleRepository.findById(article.getArticleID());
            if(draft.isPresent()){
                draft.get().setWriter(user);
                draft.get().setTitle(article.getTitle());
                draft.get().setAbstract(article.getAbstract());
                draft.get().setContent(article.getContent());
                draft.get().setSendToEditorAt(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
                draft.get().setStatus(Status.REQUEST_REVIEW);
                articleRepository.save(draft.get());
            }
        }else{
            Article newArticle = new Article();
            newArticle.setWriter(user);
            newArticle.setTitle(article.getTitle());
            newArticle.setAbstract(article.getAbstract());
            newArticle.setContent(article.getContent());
            newArticle.setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
            newArticle.setSendToEditorAt(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
            newArticle.setStatus(Status.REQUEST_REVIEW);
            articleRepository.save(newArticle);
        }
    }
}