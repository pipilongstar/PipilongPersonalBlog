package com.pipilong.service.Impl;

import com.pipilong.annotation.ErrorLog;
import com.pipilong.exception.ModifyException;
import com.pipilong.mapper.UserMapper;
import com.pipilong.pojo.User;
import com.pipilong.service.UserService;
import com.pipilong.service.VerificationService;
import com.pipilong.utils.CodeGenerator;
import com.sun.deploy.association.RegisterFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.LoginException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author pipilong
 * @createTime 2023/1/18
 * @description
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CodeGenerator codeGenerator;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private VerificationService verificationService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Pattern patternTelephone = Pattern.compile("^1[3-9]\\d{9}$");

    private final Pattern patternEmail = Pattern.compile("^[A-Za-z0-9-._]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,6})$");
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ)
    public String register(User user, String sessionId) throws RegisterFailedException {
        String userId = codeGenerator.getCode(8);
        userMapper.registerUserToSecurity(userId, user.getEmail(), user.getTelephone());
        userMapper.registerUserToData(userId, user.getUserName());
        String key = "user:" + sessionId;
        stringRedisTemplate.opsForValue().set(key, userId,7,TimeUnit.DAYS);
        return userId;
    }

    @ErrorLog(true)
    @Override
    public String codeLogin(String telephone, String code, String sessionId) throws LoginException {
        if(verificationService.verificationCode(code, sessionId)) throw new LoginException("验证码错误");
        String userId = null;
        try{
            userId = userMapper.getUserIdByTelephone(telephone);
            if(userId == null) throw new LoginException("1");
            String key = "user:" + sessionId;
            stringRedisTemplate.opsForValue().set(key, userId,7, TimeUnit.DAYS);
        } catch (Exception e){
            log.error(e.getMessage());
            if("1".equals(e.getMessage())) throw new LoginException("手机号未注册，请先注册");
            throw new LoginException("服务器异常");
        }
        return userId;
    }

    @Override
    public String passwordLogin(String identifier, String password, String sessionId) throws LoginException {

        Matcher matcher = patternEmail.matcher(identifier);
        Matcher matcher1 = patternTelephone.matcher(identifier);
        if(!matcher.matches()&&!matcher1.matches()) throw new LoginException("用户名格式错误");

        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String key="identifier:"+identifier;
        String count = ops.get(key);
        if("1".equals(count)) throw new LoginException("今日试错次数已上限");

        User user = userMapper.getPasswordByIdentifier(identifier);
        if(user == null) throw new LoginException("用户未注册，请先注册");

        String truePassword=user.getPassword();
        if(truePassword==null||!passwordEncoder.matches(password, truePassword)) {
            if(count==null) {
                ops.set(key,"4",1,TimeUnit.DAYS);
                count="5";
            } else{
                ops.decrement(key);
            }
            throw new LoginException("密码错误，还剩"+(Integer.parseInt(count)-1)+"次");
        }
        String key1 = "user:" + sessionId;
        stringRedisTemplate.opsForValue().set(key1, user.getUserId(),7, TimeUnit.DAYS);
        return user.getUserId();
    }

    @Override
    public void modifyProfile(User user) throws ModifyException {

        try {
            userMapper.modifyProfile(user);
        } catch (Exception e){
            log.error(e.getMessage());
            throw new ModifyException("服务器错误");
        }

    }

    @ErrorLog(true)
    @Override
    public void modifyTelephone(String telephone, String userId){

        userMapper.modifyTelephone(telephone,userId);

    }

    @ErrorLog(true)
    @Override
    public void modifyEmail(String email, String userId) {
        userMapper.modifyEmail(email,userId);
    }

    @ErrorLog(true)
    @Override
    public void modifyPassword(String oldPassword, String newPassword, String userId) throws ModifyException{

        String encodePassword = passwordEncoder.encode(newPassword);
        if("".equals(oldPassword)){
            userMapper.setPassword(encodePassword,userId);
            return;
        }
        String saveOldPassword = userMapper.selectPassword(userId);
        if(!passwordEncoder.matches(oldPassword, saveOldPassword)) throw new ModifyException("当前密码输入错误");
        userMapper.modifyPassword(encodePassword,userId);

    }
}





























