package com.lineyou.UserService.net;

import com.lineyou.UserService.channel.InnerChannel;
import com.lineyou.UserService.entity.InnerMsg;
import com.lineyou.UserService.net.handler.MessageDelegatingHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.mqtt.MqttConnectMessage;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttMessageBuilders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 业务处理器
 *
 * @author Jun
 * @date 2020-07-09 10:10
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class BizHandler extends SimpleChannelInboundHandler<MqttMessage> {

    private MessageDelegatingHandler delegatingHandler;

    public BizHandler(MessageDelegatingHandler delegatingHandler) {
        this.delegatingHandler = delegatingHandler;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MqttMessage msg) throws Exception {
        //异常处理
        if (msg.decoderResult().isFailure()) {
            exceptionCaught(ctx, msg.decoderResult().cause());
            return;
        }

        delegatingHandler.handle(ctx, msg);
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        //登入
        MqttConnectMessage build = MqttMessageBuilders.connect()
                .cleanSession(false)
                .clientId("user-service")
                .keepAlive(60)
                .password("user.getPassword().getBytes()".getBytes())
                .username("user-service")
                .build();
        ctx.writeAndFlush(build);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ChannelId id = ctx.channel().id();
        log.info("channel:{} inactive", id.asShortText());

        //连接断开，开启断线重连机制
        InnerChannel.notify(InnerMsg.success(InnerMsg.InnerMsgEnum.reconnect));
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(cause.getMessage(), cause);
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

    }
}
