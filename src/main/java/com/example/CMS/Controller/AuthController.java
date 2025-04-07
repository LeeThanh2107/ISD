package com.example.CMS.Controller;

import com.example.CMS.Common.ResponseUtils;
import com.example.CMS.Model.User;
import com.example.CMS.Services.JwtService;
import com.example.CMS.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Lấy user từ database
        Optional<User> user = userService.findByUsername(loginRequest.getUsername());
        if (user.isEmpty() || !passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai tài khoản hoặc mật khẩu!");
        }

        // Tạo JWT token
        String token = JwtService.generateToken(user.get().getUsername(),user.get().getRole().name());
        System.out.println("Authentication: "+token);
        // Trả về response
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("name", user.get().getName());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody LoginRequest loginRequest) {
        Optional<User> user = userService.findByUsername(loginRequest.getUsername());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Không tìm thấy tài khoản!");
        }
        try {
            user.get().setPassword(this.passwordEncoder.encode(loginRequest.getPassword()));
            userService.resetPassword(user.get());
            return ResponseUtils.success("Change password successfully");
        }catch(Exception e){
            return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR,"Something bad happened!");
        }
    }
    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
