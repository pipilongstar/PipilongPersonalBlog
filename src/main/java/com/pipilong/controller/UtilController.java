package com.pipilong.controller;

import com.pipilong.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author pipilong
 * @createTime 2023/8/7
 * @description
 */
@RestController
@RequestMapping("/util")
public class UtilController {

    @Autowired
    private UtilService utilService;

    @GetMapping("/token")
    public ResponseEntity<String> getToken(
            HttpSession httpSession
    ){
        return new ResponseEntity<>(utilService.getToken(httpSession.getId()), HttpStatus.OK);
    }

}
