package com.pipilong.enums;

import org.jetbrains.annotations.Nullable;

/**
 * @author pipilong
 * @createTime 2023/2/11
 * @description
 */
public enum MessageType {
    LIKE("点赞", "like"),
    COMMENT("评论", "comment"),
    AT("at", "at"),
    FOLLOW("关注", "follow"),

    COLLECTION("收藏","collection"),

    READ("阅读","read"),
    DISCUSSLIKE("讨论点赞","discussLike"),
    DISCUSSCOLLECTION("讨论收藏","discussCollection"),
    DISCUSSREAD("讨论阅读","discussRead");

    private final String value;

    private final String lowerCase;
    MessageType(String value, String lowerCase) {
        this.value = value;
        this.lowerCase=lowerCase;
    }

    @Nullable
    public static MessageType getInstance(String s){
        for(MessageType messageType:MessageType.values()){
            if(s.equals(messageType.getValue())){
                return messageType;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getLowerCase(){return lowerCase;}
}
