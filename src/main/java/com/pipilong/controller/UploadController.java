package com.pipilong.controller;

import com.pipilong.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.*;

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







}
