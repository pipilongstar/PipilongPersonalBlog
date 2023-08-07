package com.pipilong.controller;

import com.alibaba.fastjson.JSON;
import com.pipilong.pojo.Comment;
import com.pipilong.pojo.Discuss;
import com.pipilong.service.DiscussService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pipilong
 * @createTime 2023/2/7
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/discuss")
public class DiscussController {

    @Autowired
    private DiscussService discussService;

    /**
     * 得到讨论信息
     * @param discussId 讨论id
     * @return 讨论信息
     */
    @GetMapping("/{discussId}")
    public ResponseEntity<String> getDiscuss(@PathVariable("discussId") String discussId){

        Discuss discuss = discussService.getDiscuss(discussId);

        return new ResponseEntity<>(JSON.toJSONString(discuss),HttpStatus.OK);
    }

    /**
     * 用户打开该篇文章后判断是否点赞和收藏
     * @param discussId 讨论id
     * @param userId 打开文章的用户id
     * @return true or false
     */
    @GetMapping("/discussUser/{discussId}/{userId}")
    public ResponseEntity<List<Boolean>> isCollectionOrLike(
            @PathVariable("discussId") String discussId,
            @PathVariable("userId") String userId
    ){
        List<Boolean> list=new ArrayList<>();
        list.add(discussService.selectIsCollection(discussId,userId));
        list.add(discussService.selectIsLike(discussId,userId));

        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    /**
     * 用户点赞
     * @param dianZan 点赞还是取消
     * @param discussId 讨论id
     * @param userId 用户id
     * @param authorId 作者id
     * @return null
     */
    @PostMapping("/dianzan")
    public ResponseEntity<String> dianZan(
            @RequestParam("dianZan") Boolean dianZan,
            @RequestParam("discussId") String discussId,
            @RequestParam("userId") String userId,
            @RequestParam("authorId") String authorId,
            @RequestParam("discussUrl") String discussUrl,
            @RequestParam("username") String username,
            @RequestParam("discussTheme") String discussTheme
    ){

        discussService.dianZan(dianZan,discussId,userId,authorId,discussUrl,username,discussTheme);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 用户点击收藏
     * @param collection 收藏还是取消
     * @param discussId 讨论id
     * @param userId 用户id
     * @param authorId 作者id
     * @return null
     */
    @PostMapping("/collection")
    public ResponseEntity<String> collection(
            @RequestParam("collection") Boolean collection,
            @RequestParam("discussId") String discussId,
            @RequestParam("userId") String userId,
            @RequestParam("authorId") String authorId
    ){

        discussService.collection(collection,discussId,userId,authorId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 提交评论
     * @param comment 评论信息
     * @return null
     */
    @PostMapping("/submitReply")
    public ResponseEntity<String> submitReply(@RequestBody Comment comment){

        discussService.submitReply(comment);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 查询评论
     * @param discussId 讨论id
     * @return 评论列表
     */
    @GetMapping("/comment/{discussId}")
    public ResponseEntity<List<Comment>> comment(@PathVariable("discussId") String discussId){

        List<Comment> list = discussService.getComment(discussId);

        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    /**
     * 因为用户量不大，则讨论被打开一次则算为阅读一次
     * @param authorId 作者id
     * @return null
     */
    @PostMapping("/read")
    public ResponseEntity<String> read(
            @RequestParam("authorId") String authorId,
            @RequestParam("discussId") String discussId
       ){

        discussService.readDiscuss(authorId,discussId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}























