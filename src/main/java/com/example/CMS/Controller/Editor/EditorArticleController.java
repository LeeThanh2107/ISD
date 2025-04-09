package com.example.CMS.Controller.Editor;

import com.example.CMS.Common.ResponseUtils;
import com.example.CMS.DTO.ArticleDto;
import com.example.CMS.Model.Article;
import com.example.CMS.Model.Reviews;
import com.example.CMS.Services.ArticleService;
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
    ArticleService articleService;

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
    @PostMapping("/detail/{id}")
    public ResponseEntity<?> detail(@RequestAttribute Long id){
        try{
            Article data = articleService.detail(id);
            return ResponseUtils.success(data);
        }catch(Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "Something bad happened!");
        }
    }
    @PostMapping("/review")
    public ResponseEntity<?> update(@RequestBody Reviews reviews){
        try{
//            reviewsService.update(reviews);
            return ResponseUtils.success("Edited successfully!");
        }catch(Exception e){
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR,"Something bad happened!");
        }
    }

}
