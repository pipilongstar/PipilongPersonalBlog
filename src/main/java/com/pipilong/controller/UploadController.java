package com.pipilong.controller;

import com.pipilong.exception.RepeatedSubmissionException;
import com.pipilong.pojo.Discuss;
import com.pipilong.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author pipilong
 * @createTime 2023/1/18
 * @description
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    /**
     * 上传头像
     * @return true or false
     */
    @RequestMapping("/avatar")
    public ResponseEntity<String> uploadAvatar(MultipartFile file, HttpSession httpSession) throws IOException {

        uploadService.uploadToCos(file.getInputStream(),httpSession.getId());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 上传用户发表的图片
     * @param file 图片文件
     * @return uuid，用于标识图片
     */
    @PostMapping("/image")
    public ResponseEntity<String> uploadImage(MultipartFile file) throws IOException {

        String filename = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String newFilename = uuid.substring(0,10)+'_'+filename;
        uploadService.uploadImage(file.getInputStream(),newFilename);

        return new ResponseEntity<>(uuid.substring(0,10),HttpStatus.OK);
    }

    /**
     * 上传用户发布的讨论
     * @param discuss 用户的讨论
     * @return true or false
     */
    @PostMapping("/discuss/{token}")
    public ResponseEntity<String> uploadDiscuss(
            @RequestBody Discuss discuss,
            @PathVariable("token") String token,
            HttpSession httpSession
            ) throws IOException, RepeatedSubmissionException {

        uploadService.uploadDiscuss(discuss,token,httpSession.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
