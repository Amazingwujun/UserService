package com.lineyou.UserService.entity.po;

import lombok.Data;

import java.util.Set;

/**
 * @author Jun
 * @date 2020-07-16 10:35
 */
@Data
public class UserTopic {

    private String mobile;
    private Set<String> topics;
}
