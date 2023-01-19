package com.pipilong.service.Impl;

import com.pipilong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;

/**
 * @author pipilong
 * @createTime 2023/1/19
 * @description
 */
@Service
public class MyAuthenticationProvider extends DaoAuthenticationProvider {
    private final UserService userService=new UserServiceImpl();

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String password = userDetails.getPassword();
        String sessionId = request.getSession().getId();
        if(password == null) {
            //短信验证码验证
            String code = request.getParameter("code");
            try {
                String userId = userService.codeLogin(userDetails.getUsername(),code,sessionId);
                request.getSession().setAttribute("userId",userId);
            } catch (LoginException e) {
                throw new RuntimeException(e.getMessage());
            }

        }else{
            //密码验证
            try {
                String userId = userService.passwordLogin(userDetails.getUsername(), password, sessionId);
                request.getSession().setAttribute("uesrId",userId);
            } catch (LoginException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

    }

}
