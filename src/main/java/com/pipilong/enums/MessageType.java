package com.pipilong.enums;

/**
 * @author pipilong
 * @createTime 2023/2/11
 * @description
 */
public enum MessageType {
    LIKE("点赞"),
    COMMENT("评论"),
    AT("at"),
    FOLLOW("关注");

    private final String value;

    MessageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
