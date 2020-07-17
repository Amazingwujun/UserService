package com.lineyou.UserService.controller;

import com.lineyou.UserService.constant.ResponseCode;
import com.lineyou.UserService.entity.Response;
import com.lineyou.UserService.entity.po.User;
import com.lineyou.UserService.entity.vo.FriendVO;
import com.lineyou.UserService.service.IFriendService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 好友接口
 *
 * @author Jun
 * @date 2020-07-16 14:01
 */
@RestController
@RequestMapping("friend")
public class FriendController {

    private IFriendService friendService;

    private StringRedisTemplate redisTemplate;

    @Value("${biz.sign-in-prefix:lineyou:sign-in:")
    private String signInPrefix;

    public FriendController(IFriendService friendService, StringRedisTemplate redisTemplate) {
        this.friendService = friendService;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 搜索用户
     *
     * @param mobile
     * @return
     */
    @GetMapping("search/{mobile:1[1-9][0-9]{9}}")
    public Response<FriendVO> search(@PathVariable String mobile) {
        return friendService.search(mobile);
    }

    @PostMapping("makeFriend/{mobile:1[1-9][0-9]{9}}")
    public Response<Void> makeFriend(@RequestHeader String token,
                                     @RequestHeader String principal,
                                     @PathVariable String mobile) {

        String s = redisTemplate.opsForValue().get(signInPrefix + principal);
        if (!Objects.equals(token, s)) {
            return Response.failure(ResponseCode.TOKEN_NOT_MATCH_ERR);
        }

        return friendService.makeFriend(principal, mobile);
    }
}
