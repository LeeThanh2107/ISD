package com.example.CMS.Services.Editor;

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
import java.util.List;
import java.util.stream.Collectors;
import static com.example.CMS.Common.ConvertToDTO.convertToDto;

@Service
public class EditorArticleService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ViewsRepository viewsRepository;
    public List<ArticleDto> getAllArticles() {
        List<Article> articles = articleRepository.findAllByStatusNot(Status.DRAFT);

        return articles.stream()
                .map(ConvertToDTO::convertToDto)
                .collect(Collectors.toList());
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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails) {
                User user = ((CustomUserDetails) principal).getUser();
                existedArticle.setEditor(user);
            }
        } else {
            throw new SecurityException("No authenticated user found");
        }
         existedArticle.setTitle(article.getTitle());
        existedArticle.setAbstract(article.getAbstract());
        existedArticle.setContent(article.getContent());
        existedArticle.setUpdatedAt(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
        if (article.getStatus() == Status.PUBLISHED) {
            existedArticle.setStatus(Status.PUBLISHED);
            existedArticle.setPublishedAt(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
        } else if (article.getStatus() == Status.REJECTED) {
            existedArticle.setStatus(Status.REJECTED);
        }

        articleRepository.save(existedArticle);
    }

    public void delete(Long id) {
        articleRepository.deleteById(id);
    }
}
