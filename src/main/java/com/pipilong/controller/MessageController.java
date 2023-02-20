package com.pipilong.controller;

import com.pipilong.pojo.ChatRecord;
import com.pipilong.pojo.ChatRoom;

import com.pipilong.service.MessageService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author pipilong
 * @createTime 2023/2/14
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/chatRoom/{userId}")
    public ResponseEntity<List<Object>> getChatRoom(@PathVariable("userId") String userId){

        List<Object> chatRooms = messageService.getChatRoom(userId);

        return new ResponseEntity<>(chatRooms, HttpStatus.OK);
    }

    @GetMapping("/chatRecord/{userId}/{friendId}")
    public ResponseEntity<List<ChatRecord>> getChatRecord(
            @PathVariable("userId") String userId,
            @PathVariable("friendId")String friendId){
        List<ChatRecord> records = messageService.getChatRecord(userId, friendId);

        return new ResponseEntity<>(records,HttpStatus.OK);
    }

    @GetMapping("/systemMessage/{userId}")
    public ResponseEntity<List<Object>> getSystemMessage(@PathVariable("userId") String userId){
        List<Object> list = messageService.getSystemMessage(userId);

        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @GetMapping("/likeMessage/{userId}")
    public ResponseEntity<List<Object>> getLikeMessage(@PathVariable("userId") String userId){

        List<Object> list = messageService.getLikeMessage(userId);

        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @GetMapping("/commentMessage/{userId}")
    public ResponseEntity<List<Object>> getCommentMessage(@PathVariable("userId") String userId){

        List<Object> list = messageService.getCommentMessage(userId);

        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @GetMapping("/followMessage/{userId}")
    public ResponseEntity<List<Object>> getfollowMessage(@PathVariable("userId") String userId){

        List<Object> list = messageService.getFollowMessage(userId);

        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @DeleteMapping("/deleteChatRoom/{userId}/{friendId}")
    public ResponseEntity<String> deleteChatRoom(
            @PathVariable("userId") String userId,
            @PathVariable("friendId")String friendId
    ){

        messageService.deleteChatRoom(userId,friendId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/updateNoRead")
    public ResponseEntity<String> updateNoRead(
            @RequestParam("userId") String userId,
            @RequestParam("type") String type
    ){

        messageService.updateNoRead(userId,type);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}




























