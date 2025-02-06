package com.example.jpa_todo.filter;

import com.example.jpa_todo.common.Const;
import com.example.jpa_todo.exception.ApplicationException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class LoginFilter implements Filter {

    private static final String[] WHITE_LIST = {
            "/",
            "/auth/signup",
            "/auth/login",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/v3/api-docs",
            "/webjars/**"
    };

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            if (!isWhiteList(requestURI)) {
                HttpSession session = httpRequest.getSession(false);

                if (session == null || session.getAttribute(Const.LOGIN_USER) == null) {
                    throw new ApplicationException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
                }
            }

            chain.doFilter(request, response);
        } catch (ApplicationException ex) {
            httpResponse.setStatus(ex.getStatus().value());
            httpResponse.setContentType("application/json;charset=UTF-8");

            String errorBody = String.format("""
                    {
                        "status": "%s",
                        "code": "%d",
                        "message": "%s",
                        "timestamp": "%s"
                    }
                    """, ex.getStatus().name(), ex.getStatus().value(), ex.getMessage(), LocalDateTime.now());

            PrintWriter writer = httpResponse.getWriter();
            writer.println(errorBody);
            writer.flush();
        }
    }

    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}
