package com.pipilong.webSocketEndpoint;

import com.alibaba.fastjson.JSON;
import com.pipilong.pojo.ChatRecord;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
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

    @Autowired
    private void setRabbitTemplate(RabbitTemplate rabbitTemplate){
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
    public void onMessage(String message){
        ChatRecord msg = JSON.parseObject(message,ChatRecord.class);
        Session friendSession = map.get(msg.getFriendId());
        boolean isRead = false;
        if(friendSession != null){
            friendSession.getAsyncRemote().sendText(msg.getText());
            isRead=true;
        }
        Pair<ChatRecord, Boolean> pair = new Pair<>(msg,isRead);
        rabbitTemplate.convertAndSend("chatRecordExchange","chatRecord",pair);
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
