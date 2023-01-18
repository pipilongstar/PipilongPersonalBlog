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


    @PostMapping("/avatar")
    public ResponseEntity<String> uploadAvatar(
            @RequestParam("userid") String userId,
            @RequestParam("avatarUrl") String avatarUrl
    ){

        System.out.println(userId+":"+avatarUrl);

        return null;
    }







}
