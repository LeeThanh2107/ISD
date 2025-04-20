package com.example.CMS.Controller.Admin;

import com.example.CMS.Common.ResponseUtils;
import com.example.CMS.Model.User;
import com.example.CMS.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/user")
public class UserController {
    @Autowired
    UserService userService;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // Đảm bảo bạn đã inject BCryptPasswordEncoder
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
            String rawPassword = generateRandomPassword(10);
            // Mã hóa mật khẩu trước khi lưu vào database
            String encodedPassword = passwordEncoder.encode(rawPassword);
            user.setPassword(encodedPassword); // Giả sử User có phương thức setPassword()

            // Lưu user vào database
            userService.store(user);

            // Chuẩn bị response bao gồm thông báo thành công và mật khẩu gốc
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Create user successfully!");
            response.put("generatedPassword", rawPassword);
            return ResponseUtils.success(response);
        }catch(Exception e){
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR,"Something bad happened!");
        }
    }


    // Phương thức để tạo mật khẩu ngẫu nhiên
    private String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            password.append(chars.charAt(index));
        }
        return password.toString();
    }
}
