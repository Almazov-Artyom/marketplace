package ru.almaz.authservice.filter;

import ru.almaz.authservice.service.JwtService;
import ru.almaz.authservice.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
       String authHeader = request.getHeader(AUTHORIZATION_HEADER);
       if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
           log.info("это");
           filterChain.doFilter(request, response);
           return;
       }

       String token = authHeader.substring(BEARER_PREFIX.length());
       String userName = jwtService.extractUserName(token);

       if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
           UserDetails userDetails = userService.getUserByUsername(userName);
            if(jwtService.isTokenValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
       }
       filterChain.doFilter(request, response);
    }
}
