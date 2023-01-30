package com.pipilong.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

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
     * @return true or false
     */
    @RequestMapping("/avatar")
    public ResponseEntity<String> uploadAvatar(MultipartFile file) throws FileNotFoundException {

        OutputStream os = new FileOutputStream("D://demo.jpg");

        try {
            System.out.println(file.getOriginalFilename());
            os.write(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }







}
