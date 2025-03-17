package com.example.CMS.Common;

import com.example.CMS.Model.CustomUserDetails;
import com.example.CMS.Services.CustomUserDetailService;
import com.example.CMS.Services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailService userDetailsService;

    @Autowired
    private JwtService jwtUtil; // Giả sử bạn có lớp JwtUtil để xác thực token

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + header);
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            String username = JwtService.extractUsername(token);
            String role = JwtService.extractRole(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                CustomUserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                if (jwtUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);

    }
}