package com.lineyou.UserService.service.impl;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lineyou.UserService.channel.Listener;
import com.lineyou.UserService.constant.Topic;
import com.lineyou.UserService.entity.InnerMsg;
import com.lineyou.UserService.entity.ProtoMsg;
import com.lineyou.UserService.net.NetClient;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.mqtt.MqttMessageBuilders;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.handler.codec.mqtt.MqttQoS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * @author Jun
 * @date 2020-07-16 19:09
 */
@Component
public class UserEventHandler implements Listener {

    @Value("${biz.user-prefix:lineyou:user:}")
    private String userPrefix;

    @Value("${biz.friend-prefix:lineyou:friend:}")
    private String friendPrefix;

    private NetClient netClient;

    private StringRedisTemplate redisTemplate;

    public UserEventHandler(NetClient netClient, StringRedisTemplate redisTemplate) {
        this.netClient = netClient;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void action(InnerMsg msg) {
        if (InnerMsg.InnerMsgEnum.pub == msg.getType()) {
            Object data = msg.getData();
            InnerMsg.PubMsg pubMsg = (InnerMsg.PubMsg) data;
            String topic = pubMsg.topic();
            if (Topic.USER_STATE_CHANGE.equals(topic)) {
                onUserStateChangeEvent(pubMsg.msg());
            }
        }
    }

    private void onUserStateChangeEvent(byte[] msg) {
        try {
            ProtoMsg.UserStateMessage userStateMessage = ProtoMsg.UserStateMessage.parseFrom(msg);
            String mobile = userStateMessage.getMobile();
            boolean online = userStateMessage.getOnline();
            if (StringUtils.isEmpty(mobile)) {
                return;
            }

            //变更用户状态
            redisTemplate.opsForHash().put(userPrefix + mobile, "online", String.valueOf(online));

            //搜索用户好友
            Set<String> members = redisTemplate.opsForSet().members(friendPrefix + mobile);
            members.forEach(user -> {
                MqttPublishMessage message = MqttMessageBuilders.publish()
                        .topicName(Topic.FRIEND_STATE_CHANGE + user)
                        .retained(false)
                        .qos(MqttQoS.AT_MOST_ONCE) //只通知在线的朋友
                        .payload(Unpooled.wrappedBuffer(msg))
                        .messageId(netClient.genMsgId())
                        .build();

                netClient.send(message);
            });
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
}
