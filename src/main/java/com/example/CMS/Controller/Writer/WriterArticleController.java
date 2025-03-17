package com.example.CMS.Controller.Writer;

import com.example.CMS.Common.ResponseUtils;
import com.example.CMS.Model.Article;
import com.example.CMS.Services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/writer/article")
public class WriterArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/create")
    public ResponseEntity<?> store(@RequestBody Article article){
        try{
            articleService.create(article);
            return ResponseUtils.success("Create Article Successfully");
        }catch(Exception e){
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR,"Something bad happened!");
        }
    }
}
