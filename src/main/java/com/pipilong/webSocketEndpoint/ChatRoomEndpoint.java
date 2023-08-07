package com.pipilong.webSocketEndpoint;

import com.alibaba.fastjson.JSON;
import com.pipilong.pojo.ChatRecord;

import com.pipilong.utils.Trie;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pipilong
 * @createTime 2023/2/14
 * @description
 */
@ServerEndpoint("/chatRoomSocket/{userId}")
@Component
@Slf4j
public class ChatRoomEndpoint {

    private static Map<String,Session> map;

    private static RabbitTemplate rabbitTemplate;

    private static Trie trie;

    @Autowired
    private void setRabbitTemplate(RabbitTemplate rabbitTemplate,Trie trie){
        ChatRoomEndpoint.trie=trie;
        ChatRoomEndpoint.rabbitTemplate=rabbitTemplate;
    }

    static {
        ChatRoomEndpoint.map = new HashMap<>();
    }

    @OnOpen
    public void onOpen(Session session,@PathParam("userId") String userId){

        map.put(userId,session);
        log.info(userId);
    }

    @OnMessage
    public void onMessage(String message) throws IOException {
        ChatRecord msg = JSON.parseObject(message,ChatRecord.class);
        msg.setText(trie.filterSensitiveWords(msg.getText()));
        Session friendSession = map.get(msg.getFriendId());
        boolean isRead = false;
        if(friendSession != null){
            friendSession.getAsyncRemote().sendText(msg.getText());
            isRead=true;
        }
        List<Object> list=new ArrayList<>();
        list.add(msg);
        list.add(isRead);
        rabbitTemplate.convertAndSend("chatRecordExchange","chatRecord",list);
    }

    @OnError
    public void onError(@NotNull Throwable throwable){

        log.error(throwable.toString());

    }

    @OnClose
    public void onClose(@PathParam("userId") String userId){

        map.remove(userId);

    }

}
