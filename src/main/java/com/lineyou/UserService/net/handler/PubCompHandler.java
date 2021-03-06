package com.lineyou.UserService.net.handler;


import com.lineyou.UserService.annotation.MqttHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttMessageType;
import lombok.extern.slf4j.Slf4j;

/**
 * {@link MqttMessageType#PUBCOMP} 消息处理器
 *
 * @author Jun
 * @date 2020-07-09 14:22
 */
@Slf4j
@MqttHandler(type = MqttMessageType.PUBCOMP)
public class PubCompHandler implements MqttMessageHandler {

    @Override
    public void process(ChannelHandlerContext ctx, MqttMessage msg) {
        //暂不处理
    }
}
