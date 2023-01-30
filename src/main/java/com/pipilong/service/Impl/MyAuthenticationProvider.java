package com.pipilong.service.Impl;

import com.pipilong.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
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
import javax.servlet.http.HttpSession;

/**
 * @author pipilong
 * @createTime 2023/1/19
 * @description
 */
@Slf4j
@Service
public class MyAuthenticationProvider extends DaoAuthenticationProvider {
    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession httpSession;

    public MyAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder){
        this.setUserDetailsService(userDetailsService);
        this.setPasswordEncoder(passwordEncoder);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String username = request.getParameter("username");
        if(username==null) throw new AuthenticationServiceException("未认证");
        String password = request.getParameter("password");
        String sessionId = httpSession.getId();
        if(password == null || "".equals(password)) {
            //短信验证码验证
            String code = request.getParameter("code");
            log.info("验证码："+code);
            try {
                String userId = userService.codeLogin(username,code,sessionId);
                request.getSession().setAttribute("userId",userId);
            } catch (LoginException e) {
                throw new AuthenticationServiceException(e.getMessage());
            } finally {
                log.info("短信登录验证");
            }
        }else{
            //密码验证
            try {
                String userId = userService.passwordLogin(username, password, sessionId);
                request.getSession().setAttribute("userId",userId);
            } catch (LoginException e) {
                throw new AuthenticationServiceException(e.getMessage());
            } finally {
                log.info("密码登录验证");
            }
        }

    }

}
