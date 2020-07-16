package com.lineyou.UserService.entity.po;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户
 *
 * @author Jun
 * @date 2020-07-16 10:33
 */
@Data
public class User {

    @NotBlank(message = "手机号不能为空")
    private String mobile;
    private String nickname;

    @NotBlank(message = "密码能为空")
    private String password;
    private String avatar;
}
