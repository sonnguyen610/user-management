package com.springboot.user_management.securty;

import com.springboot.user_management.constant.Constants;
import com.springboot.user_management.entity.User;
import com.springboot.user_management.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Collectors;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    public JwtFilter(@Lazy JwtUtils jwtUtils, UserRepository userRepository) {
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String requestPath = request.getRequestURI();

        if (Constants.EXCLUDED_PATHS.contains(requestPath)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Nếu không có header Authorization
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setHeader("Content-Type", "application/json");
            response.getWriter().write("{\"code\": 1001, \"message\": \"Thiếu Authorization header.\", \"success\": false}");
            return;
        }

        String token = authHeader.substring(7); // Lấy phần token sau "Bearer "

        try {
            String username = jwtUtils.getUsernameFromToken(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = userRepository.findByUsername(username).orElse(null);

                if (user != null && jwtUtils.validateAndCheckExpiration(token)) {
                    // Set authentication vào SecurityContext
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            user, null, user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
                    );
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    // Nếu thông tin đăng nhập không hợp lệ hoặc token hết hạn
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setHeader("Content-Type", "application/json");
                    response.getWriter().write("{\"code\": 1003, \"message\": \"Token hết hạn hoặc không hợp lệ.\", \"success\": false}");
                    return;
                }
            } else {
                // Nếu username không tồn tại
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setHeader("Content-Type", "application/json");
                response.getWriter().write("{\"code\": 1002, \"message\": \"Username không tồn tại.\", \"success\": false}");
                return;
            }
        } catch (JwtException | IllegalArgumentException e) {
            // Nếu xảy ra lỗi khi xử lý token
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setHeader("Content-Type", "application/json");
            response.getWriter().write("{\"code\": 1004, \"message\": \"Token không hợp lệ.\", \"success\": false}");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
