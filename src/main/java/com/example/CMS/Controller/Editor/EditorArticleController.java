package com.example.CMS.Controller.Editor;

import com.example.CMS.Common.GlobalConstants.Status;
import com.example.CMS.Common.ResponseUtils;
import com.example.CMS.DTO.ArticleDto;
import com.example.CMS.Model.Article;
import com.example.CMS.Services.Editor.EditorArticleService;
import com.example.CMS.Services.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/editor/article")
public class EditorArticleController {

    @Autowired
    EditorArticleService articleService;

    @Autowired
    ReviewsService reviewsService;

    @GetMapping("/list")
    public ResponseEntity<?> index(){
        try{
            List<ArticleDto> data = articleService.getAllArticles();
            return ResponseUtils.success(data);
        }catch(Exception e){
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR,"Something bad happened!");
        }
    }
    @PostMapping("/detail")
    public ResponseEntity<?> detail(@RequestBody IdRequest request){
        try{
            ArticleDto data = articleService.detail(request.getId());
            return ResponseUtils.success(data);
        }catch(Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    @PostMapping("/review")
    public ResponseEntity<?> update(@RequestBody ReviewRequest reviewRequest){
        try{
            articleService.update(reviewRequest.getArticle(),reviewRequest.getId());
          String result = reviewsService.create(reviewRequest.getArticle(),reviewRequest.getComments());
            return ResponseUtils.success(result);
        }catch(Exception e){
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR,"Something bad happened!");
        }
    }
    public static class IdRequest {
        private String id;
        private Status status;
        // Getters and setters
        public String getId() {
            return id;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
    public static class ReviewRequest{
        private Article article;
        private String comments;
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Article getArticle() {
            return article;
        }

        public void setArticle(Article article) {
            this.article = article;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }
    }

}
