package com.pipilong.controller;

import com.pipilong.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pipilong
 * @createTime 2023/2/11
 * @description 事件处理器
 */
@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/systemMessage")
    public ResponseEntity<String> systemMessage(
            @RequestParam("text") String text,
            @RequestParam("userId") String userId
    ){

        eventService.systemMessage(text,userId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }








}



















