package com.lineyou.UserService.net.handler;

import com.lineyou.UserService.annotation.MqttHandler;
import io.netty.channel.ChannelHandlerContext;

import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttMessageType;
import lombok.extern.slf4j.Slf4j;

/**
 * {@link MqttMessageType#PINGRESP} 消息处理器
 *
 * @author Jun
 * @date 2020-07-09 13:50
 */
@Slf4j
@MqttHandler(type = MqttMessageType.PINGRESP)
public class PingAckHandler implements MqttMessageHandler {

    @Override
    public void process(ChannelHandlerContext ctx, MqttMessage msg) {
//        log.info("pingack:{}", msg);
    }
}
