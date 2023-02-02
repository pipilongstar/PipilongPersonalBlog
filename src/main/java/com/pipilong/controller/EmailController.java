package com.pipilong.controller;

import com.pipilong.service.EmailService;
import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author pipilong
 * @createTime 2023/1/20
 * @description
 */
@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendemail")
    public ResponseEntity<String> sendEmail(@RequestParam("userid") String userId, HttpSession session) throws EmailException {

        emailService.sendVerificationCodeByUserId(userId,session.getId());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}




































