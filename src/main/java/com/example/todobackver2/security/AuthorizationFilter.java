package com.example.todobackver2.security;

import com.example.todobackver2.Exception.AuthExceptions;
import com.example.todobackver2.Exception.ErrorMessage;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;

public class AuthorizationFilter extends BasicAuthenticationFilter {
    private Key getSigningKey() {
        byte[] keyBytes = SecurityContants.getTokenSecret().getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    public AuthorizationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException{
        String header=req.getHeader(SecurityContants.HEADER_STRING);
        if(header==null ||!header.startsWith(SecurityContants.TOKEN_PREFIX)){
            chain.doFilter(req,res);
            res.setContentType("application/json");
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            res.getOutputStream().println(ErrorMessage.TOKEN_IS_NULL.getErrorMessage()+ ErrorMessage.TOKEN_IS_NULL.getStatus());
            return ;
        }
        UsernamePasswordAuthenticationToken authenticationToken=getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(req,res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req){
        String tokenHeader=req.getHeader(SecurityContants.HEADER_STRING);
        if(tokenHeader!=null){
            tokenHeader=tokenHeader.replace(SecurityContants.TOKEN_PREFIX,"");
            String user= Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(tokenHeader).getBody().getSubject();
            if(user!=null){
                return new UsernamePasswordAuthenticationToken(user,null,new ArrayList<>());
            }
            else throw new AuthExceptions(ErrorMessage.TOKEN_INVALID.getErrorMessage(), ErrorMessage.TOKEN_INVALID.getStatus());
        }
        String tokenParam=req.getParameter("authorization");
        System.out.println(tokenParam);
        if(tokenParam!=null){
            String user= Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(tokenParam).getBody().getSubject();
            if(user!=null){
                return new UsernamePasswordAuthenticationToken(user,null,new ArrayList<>());
            }
            else throw new AuthExceptions(ErrorMessage.TOKEN_INVALID.getErrorMessage(), ErrorMessage.TOKEN_INVALID.getStatus());
        }
        return null;
    }
}
