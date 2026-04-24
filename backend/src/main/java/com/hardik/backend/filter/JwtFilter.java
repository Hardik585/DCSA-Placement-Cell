package com.hardik.backend.filter;

import com.hardik.backend.model.UserEntity;
import com.hardik.backend.repository.UserRepository;
import com.hardik.backend.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            String email = jwtUtil.extractEmail(token);
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                //fetching the user from the Database
                UserEntity userEntity = userRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("User not found with this email"));
                //now checking if the token expired
                System.out.println(userEntity.getEmail());
                System.out.println(userEntity.getRole().name());
                if (jwtUtil.validateToken(userEntity.getEmail(), token)) {
                    UsernamePasswordAuthenticationToken authToken = getUsernamePasswordAuthenticationToken(request, userEntity);
                    //setting the security context
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("Security Context set : => " + SecurityContextHolder.getContext().getAuthentication());
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private static UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(HttpServletRequest request, UserEntity userEntity) {
        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + userEntity.getRole().name())
        );
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        userEntity,
                        null,
                        authorities);
        //Adding more details for auditing and more purpose
        authToken.setDetails(new WebAuthenticationDetails(request));
        return authToken;
    }
}
