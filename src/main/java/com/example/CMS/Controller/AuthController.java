package com.example.CMS.Controller;

import com.example.CMS.Model.User;
import com.example.CMS.Services.JwtService;
import com.example.CMS.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/login")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Lấy user từ database
        String fixedPassword = passwordEncoder.encode("admin@123123");
        Optional<User> user = userService.findByUsername(loginRequest.getUsername());
        if (user.isEmpty() || !passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai tài khoản hoặc mật khẩu!");
        }

        // Tạo JWT token
        String token = JwtService.generateToken(user.get().getUsername());

        // Trả về response
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("role", user.get().getRole().name()); // Giả sử User có thuộc tính `role`

        return ResponseEntity.ok(response);
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
