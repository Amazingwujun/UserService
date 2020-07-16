package com.lineyou.UserService.service;

import com.lineyou.UserService.entity.Response;
import com.lineyou.UserService.entity.po.User;
import com.lineyou.UserService.entity.dto.SignUpUserDTO;

/**
 * @author Jun
 * @date 2020-07-16 10:49
 */
public interface IUserService {

    /**
     * 用户注册
     *
     * @param signUpUserDTO
     * @return
     */
    Response<Void> signUp(SignUpUserDTO signUpUserDTO);

    /**
     * 用户登入
     *
     * @param user
     * @return
     */
    Response<?> signIn(User user);
}
