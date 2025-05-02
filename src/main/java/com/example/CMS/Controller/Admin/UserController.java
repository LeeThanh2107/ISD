package com.example.CMS.Controller.Admin;

import com.example.CMS.Common.ResponseUtils;
import com.example.CMS.DTO.UserDTO;
import com.example.CMS.Model.User;
import com.example.CMS.Services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/admin/user")
public class UserController {
    @Autowired
    UserService userService;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // Đảm bảo bạn đã inject BCryptPasswordEncoder
    @GetMapping("/list")
    public ResponseEntity<?> index(){
        try{
            List<UserDTO> data = userService.getAllUser();
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
    public ResponseEntity<?> delete(@PathVariable Long id){
        try {
            userService.delete(id); // Service now handles the selective logic
            return ResponseEntity.noContent().build(); // Return HTTP 204 No Content
        } catch (EntityNotFoundException e) {
            // Handle appropriately (e.g., using @ControllerAdvice or return directly)
            return ResponseEntity.notFound().build(); // Simple 404
        } catch (Exception e) {
            // Log the full exception for server issues
            // log.error("Error deleting user with id {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Simple 500
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

    @PutMapping("/edit-account")
    public ResponseEntity<?> edit(@RequestBody User user){
        try{
            Optional<User> userEdit = userService.findByUsername(user.getUsername());
            // Lưu user vào database
            if(userEdit.isPresent()){
                userEdit.get().setName(user.getName());
                userEdit.get().setEmail(user.getEmail());
                userEdit.get().setRole(user.getRole());
                userService.store(userEdit.get());
            }
            return ResponseUtils.success("Edit successfully");
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
