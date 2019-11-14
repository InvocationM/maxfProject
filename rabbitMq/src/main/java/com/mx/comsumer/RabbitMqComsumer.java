package com.mx.comsumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 描述:
 * ----消费者
 *
 * @author Administrator
 * @create 2019-11-14 22:33
 */
public class RabbitMqComsumer {

    private static final String QUEUE_NAME = "queue_demo";
    private static final String IP_ADDRESS = "47.98.141.221";
    private static final int PORT = 5672;

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        Address[] addresses = new Address[]{new Address(IP_ADDRESS, PORT)};

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("admin");
        factory.setPassword("admin");

        Connection connection = factory.newConnection(addresses);

        final Channel channel = connection.createChannel();

        channel.basicQos(64);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("zzzzzzzzzzzzzzz" + new String(body));

//                try {
////                    TimeUnit.SECONDS.sleep(5);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        channel.basicConsume(QUEUE_NAME, consumer);

        TimeUnit.SECONDS.sleep(5);
//
        channel.close();
        connection.close();
    }

}