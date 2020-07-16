package com.lineyou.UserService.service;

import com.lineyou.UserService.entity.Response;
import com.lineyou.UserService.entity.po.User;

/**
 * @author Jun
 * @date 2020-07-16 14:05
 */
public interface IFriendService {


    Response<User> search(String mobile);

    Response<Void> makeFriend(String principal, String mobile);
}
