package com.jazzybruno.example.v1.security.jwt;

import com.jazzybruno.example.v1.repositories.UserRepository;
import com.jazzybruno.example.v1.security.user.UserSecurityDetails;
import com.jazzybruno.example.v1.security.user.UserSecurityDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final UserSecurityDetailsService userSecurityDetailsService;
    private final JwtUtils jwtUtils;

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            final String authHeader = request.getHeader(AUTHORIZATION);
            final String userEmail;
            final String jwtToken;

            if(authHeader == null || !authHeader.startsWith("Bearer")){
                filterChain.doFilter(request , response);
                throw new RuntimeException("No Token is present");
//                return;
            }

            jwtToken = authHeader.substring(7);
            JwtUserInfo jwtUserInfo = jwtUtils.decodeToken(jwtToken);
            userEmail= jwtUserInfo.getEmail();
            if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserSecurityDetails userSecurityDetails = (UserSecurityDetails) userSecurityDetailsService.loadUserByUsername(userEmail);
                if(jwtUtils.isTokenValid(jwtToken , userSecurityDetails)){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userSecurityDetails , null , userSecurityDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request , response);
        }catch (RuntimeException e){
            e.printStackTrace();
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            throw new RuntimeException("Something went wrong");
        }
    }
}
