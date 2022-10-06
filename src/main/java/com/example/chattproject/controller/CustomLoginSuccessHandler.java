package com.example.chattproject.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("login success");
        List<String> rolenames = new ArrayList<>();
        authentication.getAuthorities().forEach(grantedAuthority -> {
            rolenames.add(grantedAuthority.getAuthority());
        });
        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect("/");
    }
}
