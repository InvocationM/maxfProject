package com.mx.producer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 描述:
 * ----生产者
 *
 * @author Administrator
 * @create 2019-11-14 20:55
 */
public class RabbitMqProducer {

    private static final String EXCHANG_NAME = "exchang_demo";
    private static final String ROUTING_KEY = "routkey_demo";
    private static final String QUEUE_NAME = "queue_demo";
    private static final String IP_ADDRESS = "47.98.141.221";
    private static final int PORT = 5672;

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(IP_ADDRESS);
        connectionFactory.setPort(PORT);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        // 设置交换器
        channel.exchangeDeclare(EXCHANG_NAME, "direct", true, false, null);

        // 设置队列
        channel.queueDeclare(QUEUE_NAME, true, false,  false, null);

        // 队列和交换器 使用路由键绑定
        channel.queueBind(QUEUE_NAME, EXCHANG_NAME, ROUTING_KEY);

        // hello world!
        String message = "Hello world !!!";

        // 发送
        channel.basicPublish(EXCHANG_NAME,  ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());

//        channel.close();
//        connection.close();
    }


}