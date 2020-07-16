package com.lineyou.UserService.service.impl;

import com.lineyou.UserService.constant.ResponseCode;
import com.lineyou.UserService.entity.Response;
import com.lineyou.UserService.entity.dto.SignUpUserDTO;
import com.lineyou.UserService.entity.po.User;
import com.lineyou.UserService.entity.vo.LoginVO;
import com.lineyou.UserService.service.IUserService;
import com.lineyou.UserService.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 用户服务实现
 *
 * @author Jun
 * @date 2020-07-16 10:49
 */
@Service
public class UserServiceImpl implements IUserService {

    @Value("${biz.hash-salt:&*fjk.*6324.)456}")
    private String hashSalt;

    @Value("${biz.topic-prefix:MQTT:TOPIC:}")
    private String topicPrefix;

    @Value("${biz.topic-set-key:MQTT:ALLTOPIC}")
    private String topicSetKey;

    @Value("${biz.user-prefix:lineyou:user:}")
    private String userPrefix;

    @Value("${biz.sign-in-prefix:lineyou:sign-in:")
    private String signInPrefix;

    @Value("${biz.friend-prefix:lineyou:friend:}")
    private String friendPrefix;

    private StringRedisTemplate redisTemplate;

    public UserServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Response<Void> signUp(SignUpUserDTO signUpUserDTO) {
        String mobile = signUpUserDTO.getMobile();
        String password = signUpUserDTO.getPassword();

        //用户查重
        if (Boolean.TRUE.equals(redisTemplate.hasKey(userPrefix + mobile))) {
            return Response.failure(ResponseCode.DUPLICATE_DATA_ERR.getCode(), "用户已存在");
        }

        //开始注册
        signUpUserDTO.setPassword(passwordEncode(password));
        redisTemplate.opsForHash().putAll(userPrefix + mobile, BeanUtils.bean2map(signUpUserDTO));

        return Response.success();
    }

    @Override
    public Response<?> signIn(User user) {
        final String mobile = user.getMobile();
        String password = user.getPassword();

        String userKey = userPrefix + mobile;
        //用户存在与否
        if (Boolean.FALSE.equals(redisTemplate.hasKey(userKey))) {
            return Response.failure(ResponseCode.INFO_NOT_FOUND_ERR.getCode(), "用户不存在");
        }

        //密码校验
        @SuppressWarnings("rawtypes") Map entries = redisTemplate.opsForHash().entries(userKey);
        @SuppressWarnings("unchecked") User u = BeanUtils.map2bean(entries, User.class);
        if (!passwordMatch(u.getPassword(), password)) {
            return Response.failure(ResponseCode.LOGIN_ERR);
        }

        //登入成功，置入 token
        String token = genToken(mobile);
        redisTemplate.opsForValue().set(signInPrefix + mobile,token, Duration.ofDays(3));

        //response vo
        LoginVO loginVO = new LoginVO();
        loginVO.setMobile(mobile);
        loginVO.setNickname(u.getNickname());
        loginVO.setToken(token);

        //获取用户订阅的 topic，这里与 mqttx 项目紧密耦合
        Set<String> topics = new HashSet<>();
        Set<String> keys = redisTemplate.opsForSet().members(topicSetKey);
        if (!CollectionUtils.isEmpty(keys)) {
            keys.forEach(topic -> {
                if (Boolean.TRUE.equals(redisTemplate.opsForHash().hasKey(topicPrefix + topic, mobile))) {
                    topics.add(topic);
                }
            });
        }
        loginVO.setSubTopics(topics);

        //好友获取
        Set<String> friends = redisTemplate.opsForSet().members(friendPrefix + mobile);
        loginVO.setFriends(friends);

        return Response.success(loginVO);
    }

    private String passwordEncode(String password) {
        String token = password + hashSalt;
        return DigestUtils.md5DigestAsHex(token.getBytes(StandardCharsets.UTF_8));
    }

    private boolean passwordMatch(String encoded, String password) {
        return Objects.equals(encoded, passwordEncode(password));
    }

    private String genToken(String mobile) {
        String token = mobile + System.currentTimeMillis() + ThreadLocalRandom.current().nextInt(10000);
        return DigestUtils.md5DigestAsHex(token.getBytes());
    }
}
