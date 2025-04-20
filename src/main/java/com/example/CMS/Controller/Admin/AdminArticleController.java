package com.example.CMS.Controller.Admin;

import com.example.CMS.Common.ResponseUtils;
import com.example.CMS.DTO.ArticleDto;
import com.example.CMS.DTO.ViewsDto;
import com.example.CMS.Services.Admin.AdminArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/article")

public class AdminArticleController {
    @Autowired
    private AdminArticleService adminArticleService;

        @GetMapping("/list")
    public ResponseEntity<?> index(){
        try{
            List<ArticleDto> articles = adminArticleService.index();
            return ResponseUtils.success(articles);
        }catch(Exception e){
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR,"Có gì đó không hay xảy ra!");
        }
    }
}
