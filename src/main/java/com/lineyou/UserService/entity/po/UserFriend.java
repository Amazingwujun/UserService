package com.lineyou.UserService.entity.po;

import lombok.Data;

import java.util.Set;

/**
 * @author Jun
 * @date 2020-07-16 10:37
 */
@Data
public class UserFriend {

    private String mobile;

    /** 朋友 mobile */
    private Set<String> friends;
}
