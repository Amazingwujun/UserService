package com.lineyou.UserService.service.impl;

import com.lineyou.UserService.constant.ResponseCode;
import com.lineyou.UserService.entity.Response;
import com.lineyou.UserService.entity.po.User;
import com.lineyou.UserService.net.NetClient;
import com.lineyou.UserService.service.IFriendService;
import com.lineyou.UserService.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * 好友服务实现
 *
 * @author Jun
 * @date 2020-07-16 14:06
 */
@Service
public class FriendServiceImpl implements IFriendService {

    @Value("${biz.user-prefix:lineyou:user:}")
    private String userPrefix;

    @Value("${biz.friend-prefix:lineyou:friend:}")
    private String friendPrefix;

    private StringRedisTemplate redisTemplate;

    private NetClient netClient;

    public FriendServiceImpl(StringRedisTemplate redisTemplate, NetClient netClient) {
        this.redisTemplate = redisTemplate;
        this.netClient = netClient;
    }

    @Override
    public Response<User> search(String mobile) {
        Map entries = redisTemplate.opsForHash().entries(userPrefix + mobile);
        if (CollectionUtils.isEmpty(entries)) {
            return Response.failure(ResponseCode.INFO_NOT_FOUND_ERR.getCode(), "用户不存在");
        }

        User r = BeanUtils.map2bean(entries, User.class);
        r.setPassword(null);
        return Response.success(r);
    }

    @Override
    public Response<Void> makeFriend(String principal, String mobile) {
        if (Boolean.FALSE.equals(redisTemplate.hasKey(userPrefix + mobile))) {
            return Response.failure(ResponseCode.INFO_NOT_FOUND_ERR.getCode(), "对方不存在");
        }

        //todo 交友验证未实现

        redisTemplate.opsForSet().add(friendPrefix + mobile, principal);
        redisTemplate.opsForSet().add(friendPrefix + principal, mobile);
        return Response.success();
    }
}
