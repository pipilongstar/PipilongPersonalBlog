package com.pipilong.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.pipilong.mapper.SelectExistMapper;
import com.pipilong.mapper.UserMapper;
import com.pipilong.utils.CodeGenerator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.View;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author pipilong
 * @createTime 2023/2/1
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/login/oauth2/code")
public class Oauth2Login {

    @Value("${spring.security.oauth2.client.registration.github.client-id}")
    private String githubClientId;
    @Value("${spring.security.oauth2.client.registration.github.client-secret}")
    private String githubClientSecret;
    @Value("${spring.security.oauth2.client.registration.github.redirect-uri}")
    private String githubrRedirectUri;
    @Value("${spring.security.oauth2.client.provider.github.token-uri}")
    private String githubTokenUri;
    @Value("${spring.security.oauth2.client.provider.github.user-info-uri}")
    private String githubUserInfoUri;
    @Value("${spring.security.oauth2.client.provider.github.authorization-uri}")
    private String githubAuthorizationUri;

    @Value("${spring.security.oauth2.client.registration.gitee.client-id}")
    private String giteeClientId;
    @Value("${spring.security.oauth2.client.registration.gitee.client-secret}")
    private String giteeClientSecret;
    @Value("${spring.security.oauth2.client.registration.gitee.redirect-uri}")
    private String giteerRedirectUri;
    @Value("${spring.security.oauth2.client.provider.gitee.token-uri}")
    private String giteeTokenUri;
    @Value("${spring.security.oauth2.client.provider.gitee.user-info-uri}")
    private String giteeUserInfoUri;
    @Value("${spring.security.oauth2.client.provider.gitee.authorization-uri}")
    private String giteeAuthorizationUri;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private String sessionId;
    private String username;
    private String userId;
    @Autowired
    private CodeGenerator codeGenerator;

    @Autowired
    private SelectExistMapper selectExistMapper;
    /**
     * 实现GitHub登录
     * @return
     */
//    @GetMapping("/github")
//    public String github(HttpServletRequest request){
//
//        return "redirect:/login/oauth2/code/home";
//    }
//
//    @GetMapping("/home")
//    public ResponseEntity<String> home(Model model, OAuth2AuthenticationToken authentication) {
//        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
//                authentication.getAuthorizedClientRegistrationId(),
//                authentication.getName());
//        String token = generateJwtToken(client.getAccessToken().getTokenValue());
//        model.addAttribute("token", token);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
    @GetMapping("/github/login")
    public ResponseEntity<String> githubLogin(HttpSession session) {
        String authorizationUrl = githubAuthorizationUri + "?client_id=" + githubClientId + "&redirect_uri=" + githubrRedirectUri +"&scope=user"+"&expires_in=3600*24*7";
        sessionId=session.getId();
        return new ResponseEntity<>(authorizationUrl,HttpStatus.OK);
    }

    @GetMapping("/github")
    public void githubLoginCallback(@RequestParam("code") String code, HttpServletResponse response) throws IOException {
        String jwtToken = this.oauth2Login(code,githubClientId,githubClientSecret,githubrRedirectUri,githubTokenUri,githubUserInfoUri,"github");
        String key = "user:" + this.sessionId;
        stringRedisTemplate.opsForValue().set(key,this.userId,7, TimeUnit.DAYS);
        response.sendRedirect("/index.html?jwt=" + jwtToken);
    }

    @GetMapping("/gitee/login")
    public ResponseEntity<String> giteeLogin() {
        String authorizationUrl = giteeAuthorizationUri + "?client_id=" + giteeClientId + "&redirect_uri="+ giteerRedirectUri+"&response_type=code" +"&scope=user_info emails"+"&expires_in=3600*24*7";

        return new ResponseEntity<>(authorizationUrl,HttpStatus.OK);
    }

    @GetMapping("/gitee")
    public ResponseEntity<String> giteeLoginCallback(@RequestParam("code") String code) {
        String jwtToken = this.oauth2Login(code,giteeClientId,giteeClientSecret,giteerRedirectUri,giteeTokenUri,giteeUserInfoUri,"gitee");

        return new ResponseEntity<>(jwtToken,HttpStatus.OK);
    }

    private String extractUsername(String userResponse,String type) {
        Map<String,String> userData =JSON.parseObject(userResponse,new TypeReference<Map<String, String>>() {});
        if("github".equals(type)) {
            this.username=userData.get("login");
            Integer userId = selectExistMapper.githubIdIfExist(userData.get("id"));
            if(userId!=null){
                this.userId=userId.toString();
                return this.username;
            }
            this.userId=codeGenerator.getCode(8);
            userData.put("userId",this.userId);
            rabbitTemplate.convertAndSend("oauth2LoginExchange","github",userData);
        }
        else if("gitee".equals(type)) {

            rabbitTemplate.convertAndSend("oauth2LoginExchange","gitee",userData);
        }
        return this.username;
    }

    private String extractAccessToken(String accessTokenResponse) {
        int start = accessTokenResponse.indexOf('=')+1;
        int end = accessTokenResponse.indexOf('&');
        return accessTokenResponse.substring(start,end);
    }

    /**
     * 生成jwt令牌
     * @param accessToken 授权服务器给的令牌
     * @return jwt令牌
     */
    private String generateJwtToken(String username,String accessToken) {
        return Jwts.builder()
                .setSubject(username)
                .claim("access_token", accessToken)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000*24))
                .signWith(SignatureAlgorithm.HS512, "FDSfsdfFdslafjldsajfljlagdFDLfsdfsDFS23fda")
                .compact();
    }

    /**
     * oauth2登录成功后返回jwt
     * @return
     */
    private String oauth2Login(String code,String clientId,String clientSecret,String redirectUri,String tokenUri,String userInfoUri,String type){
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("code", code);
        formData.add("redirect_uri", redirectUri);
        formData.add("expires_in","3600*24*7");
        //获取令牌
        String accessTokenResponse = restTemplate.postForObject(tokenUri, formData, String.class);
        assert accessTokenResponse != null;
        //解析出令牌
        String accessToken = extractAccessToken(accessTokenResponse);
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+accessToken);
        headers.add("Content-Type", "application/json");
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        //获取用户信息
        ResponseEntity<String> userResponse = restTemplate.exchange(userInfoUri, HttpMethod.GET, httpEntity,String.class);

        String username = extractUsername(userResponse.getBody(),type);
        return generateJwtToken(username, accessToken);
    }



























}
