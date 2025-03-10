package com.example.CMS.Controller.Admin;

import com.example.CMS.Common.ResponseUtils;
import com.example.CMS.Model.Article;
import com.example.CMS.Model.User;
import com.example.CMS.Services.ArticleService;
import com.example.CMS.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/list")
    public ResponseEntity<?> index(){
        try{
            List<User> data = userService.index();
            return ResponseUtils.success(data);
        } catch (Exception e) {
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR,"Something bad happened!");
        }
    }
    @PostMapping("/detail/{id}")
    public ResponseEntity<?> detail(@RequestAttribute Long id){
        try{
            if (userService.detail(id) == null) {
                return ResponseUtils.error(HttpStatus.NOT_FOUND,"Article not found!");
            }
            User data = userService.detail(id);
            return ResponseUtils.success(data);
        }catch(Exception e){
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR,"Something bad happened!");
        }
    } 
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@RequestAttribute Long id){
        try{
            userService.delete(id);
            return ResponseUtils.success("Delete successfully!");
        }catch(Exception e){
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR,"Something bad happened!");
        }
    }
    @PostMapping("/store")
    public ResponseEntity<?> store(@RequestBody User user){
        try{
            userService.store(user);
            return ResponseUtils.success("Create user successfully!");
        }catch(Exception e){
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR,"Something bad happened!");
        }
    }
}
