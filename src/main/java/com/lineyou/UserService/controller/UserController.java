package com.lineyou.UserService.controller;

import com.lineyou.UserService.entity.Response;
import com.lineyou.UserService.entity.po.User;
import com.lineyou.UserService.entity.dto.SignUpUserDTO;
import com.lineyou.UserService.service.IUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 用户接口
 *
 * @author Jun
 * @date 2020-07-16 10:31
 */
@RestController
@RequestMapping("user")
public class UserController {

    private IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("signUp")
    public Response<Void> signUp(@Valid @RequestBody SignUpUserDTO signUpUserDTO){
        return userService.signUp(signUpUserDTO);
    }

    @PostMapping("signIn")
    public Response<?> signIn(@Valid @RequestBody User user){
        return userService.signIn(user);
    }
}
