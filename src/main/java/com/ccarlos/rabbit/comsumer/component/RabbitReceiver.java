package com.ccarlos.rabbit.comsumer.component;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class RabbitReceiver {

    /**
     * @description: 组合使用监听 @RabbitListener @QueueBinding @Queue @Exchange
     * @author: ccarlos
     * @date: 2020/3/27 21:45
     * @param: message
     * @param: channel
     * @return: void
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue-7", durable = "true"),
            exchange = @Exchange(name = "exchange-7",
                    durable = "true",
                    type = "topic",
                    ignoreDeclarationExceptions = "true"),
            key = "springboot.*"
    )

    )
    @RabbitHandler
    public void onMessage(Message message, Channel channel) throws Exception {
        // 1. 收到消息以后进行业务端消费处理
        System.out.println("------------------------");
        System.out.println("消费消息:"+message.getPayload());

        // 2. 处理成功之后 获取deliverTag 并进行手工的ACK操作
        Long deliverTag=(Long)message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);

        channel.basicAck(deliverTag,false);
    }
}
