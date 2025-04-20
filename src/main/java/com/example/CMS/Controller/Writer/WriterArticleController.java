package com.example.CMS.Controller.Writer;

import com.example.CMS.Common.GlobalConstants.Status;
import com.example.CMS.Common.ResponseUtils;
import com.example.CMS.DTO.ArticleDto;
import com.example.CMS.Model.Article;
import com.example.CMS.Services.Writer.WriterArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/writer/article")
public class WriterArticleController {

    @Autowired
    private WriterArticleService articleService;

    @PostMapping("/save-draft")
    public ResponseEntity<?> saveDraft(@RequestBody Article article){
        try{
            articleService.saveDraft(article);
            return ResponseUtils.success("Save draft Successfully");
        }catch(Exception e){
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR,"Something bad happened!");
        }
    }
    @GetMapping("/list")
    public ResponseEntity<?> index(){
        try{
            List<ArticleDto> articleList = articleService.getAllArticles();
            return ResponseUtils.success(articleList);
        }catch(Exception e){
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR,"Something bad happened!");
        }
    }
    @PostMapping("/send-to-editor")
    public ResponseEntity<?> sendToEditor(@RequestBody Article article){
        try{
            articleService.sendToEditor(article);
            return ResponseUtils.success("Send to editor successfully");
        }catch(Exception e){
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR,"Something bad happened!");
        }
    }
    @GetMapping("/get-most-recent-article")
    public ResponseEntity<?> getMostRecentArticle(){
        try{
            return ResponseUtils.success(articleService.getMostRecentArticlesByStatus());
        }catch(Exception e){
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR,"Có gì đó không ổn xảy ra!");
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

}
