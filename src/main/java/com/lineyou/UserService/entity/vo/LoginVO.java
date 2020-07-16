package com.lineyou.UserService.entity.vo;

import lombok.Data;

import java.util.Set;

/**
 * @author Jun
 * @date 2020-07-16 11:50
 */
@Data
public class LoginVO {

    private String mobile;
    private String token;
    private String nickname;
    private Set<String> friends;
    private Set<String> subTopics;
}
