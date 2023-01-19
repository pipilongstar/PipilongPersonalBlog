package com.pipilong.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author pipilong
 * @createTime 2023/1/18
 * @description
 */
@RestController
@RequestMapping("/upload")
public class UploadController {


    /**
     * 上传头像
     * @param userId 用户id
     * @param avatarUrl 头像URL
     * @return true or false
     */
    @PostMapping("/avatar")
    public ResponseEntity<String> uploadAvatar(
            @RequestParam("userid") String userId,
            @RequestParam("avatarUrl") String avatarUrl
    ){

        System.out.println(userId+":"+avatarUrl);

        return null;
    }







}
