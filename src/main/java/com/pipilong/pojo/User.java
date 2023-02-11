package com.pipilong.pojo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author pipilong
 * @createTime 2023/1/18
 * @description
 */
@Data
@Component
public class User {

    private String userId;
    @JsonAlias("username")
    @JsonProperty("username")
    private String userName;
    private String email;
    private String telephone;
    private String password;
    private String gender;
    private String birthday;
    private String address;
    @JsonProperty("person_message")
    @JsonAlias("person_message")
    private String personalMessage;
    @JsonProperty("github_url")
    @JsonAlias("github_url")
    private String githubUrl;
    private String school;
    private Integer numberOfRead;
    private Integer numberOfLikes;
    private Integer numberOfCollections;

    public void setProfile(User user){
        this.userId=user.getUserId();
        this.userName=user.getUserName();
        this.gender=user.getGender();
        this.birthday=user.getBirthday();
        this.address=user.getAddress();
        this.githubUrl=user.getGithubUrl();
        this.school=user.getSchool();
        this.personalMessage=user.getPersonalMessage();
        this.numberOfRead=user.getNumberOfRead();
        this.numberOfCollections=user.getNumberOfCollections();
        this.numberOfLikes=user.getNumberOfLikes();
    }

}


















