package com.pipilong.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author pipilong
 * @createTime 2023/1/20
 * @description
 */
@Component
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("text/html; charset = utf-8");
        PrintWriter writer = response.getWriter();
        if("未认证".equals(exception.getMessage())){
            response.setStatus(401);
            writer.println("未认证");
            return;
        }
        response.setStatus(400);
        writer.write(new String(exception.getMessage().getBytes(), StandardCharsets.UTF_8));
    }
}
