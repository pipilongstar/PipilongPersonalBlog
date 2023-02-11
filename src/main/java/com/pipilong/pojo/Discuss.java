package com.pipilong.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author pipilong
 * @createTime 2023/2/4
 * @description
 */
@Data
@Component
public class Discuss implements Serializable {

    private Long id;
    private Long discussId;
    private String username;
    private String theme;
    @JsonProperty("user_id")
    private Long userId;
    private String userAvatarUrl;
    private Long numberOfCollections;
    private Long numberOfLikes;
    private Long numberOfRead;
    private String label;
    @JsonProperty("date")
    private String submitDate;
    private String text;

}
















