package com.example.demo.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Component
public class JWTAuthenticationVerficationFilter extends BasicAuthenticationFilter{

    public JWTAuthenticationVerficationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        try{
            String header = req.getHeader(SecurityConstants.header_string);

            if(header != null || header.startsWith(SecurityConstants.token_prefix)){
                UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

                SecurityContextHolder.getContext().setAuthentication(authentication);
                chain.doFilter(req, res);
            }
            else{
                chain.doFilter(req, res);
                return;
            }
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {
        try{
            String token = req.getHeader(SecurityConstants.header_string);

            if (token == null){
                return null;
            }
            else{

                if (token != null) {
                    String user = JWT.require(HMAC512(SecurityConstants.secret.getBytes())).build()
                            .verify(token.replace(SecurityConstants.token_prefix, ""))
                            .getSubject();

                    if(user == null){
                        return null;

                    }else{
                        return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                    }
            }
            }
            return null;

        } catch (JWTVerificationException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }
}
