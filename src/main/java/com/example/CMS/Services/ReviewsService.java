package com.example.CMS.Services;

import com.example.CMS.Model.Article;
import com.example.CMS.Model.CustomUserDetails;
import com.example.CMS.Model.Reviews;
import com.example.CMS.Model.User;
import com.example.CMS.Repository.ReviewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewsService{

    @Autowired
    ReviewsRepository reviewsRepository;

    public String create(Article article, String comments){
        try{
            Reviews reviews = new Reviews();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails) {
                User user = ((CustomUserDetails) principal).getUser(); // Giả sử getWriter() trả về User
                reviews.setUser(user);
            }
            reviews.setArticle(article);
            reviews.setComments(comments);
            reviews.setReviewedAt(LocalDateTime.now());
            reviewsRepository.save(reviews);
            return "Thành công";
        }catch(Exception e){
            return e.toString();
        }
    }
}
