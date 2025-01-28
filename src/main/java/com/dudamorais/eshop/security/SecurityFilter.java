package com.dudamorais.eshop.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.dudamorais.eshop.exceptions.UserNotFound;
import com.dudamorais.eshop.user.User;
import com.dudamorais.eshop.user.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        if(requestURI.equals("/auth/register") || requestURI.equals("/auth/login") || requestURI.equals("/product/get") || requestURI.equals("/type/get")){
            filterChain.doFilter(request, response);
            return;
        }

        try{
            String token = recoverToken(request);

            if(token != null){
                String tokenValidation = tokenService.validateToken(token);

                User user = userRepository.findByUsername(tokenValidation)
                .orElseThrow(() -> new UserNotFound("User not found in Security Filter"));

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        }catch (JWTVerificationException ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"" + ex.getMessage() + "\"}");
            return;
        } catch (Exception ex) {
            System.err.println("Erro no filtro: " + ex.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro interno no servidor");
        }
    }

    public String recoverToken(HttpServletRequest request){
        return Optional.ofNullable(request.getCookies())
        .flatMap(cookies -> Arrays.stream(cookies)
            .filter(cookie -> "jwt".equals(cookie.getName()))
            .findFirst())
            .map(Cookie::getValue)
            .orElseThrow(() -> new JWTVerificationException("JWT Token not found"));
    }

}
